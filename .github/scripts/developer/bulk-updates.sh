#!/bin/bash
#
# Script: bulk-updates.sh
# Purpose: Apply bulk updates across all 41 Train Ticket services
# Usage: bash bulk-updates.sh [action] [args]
#
# Actions:
#   dockerfiles         - Update all Dockerfiles (requires OLD_IMAGE NEW_IMAGE)
#   pom-version         - Update pom.xml versions (requires OLD_VERSION NEW_VERSION)
#   java-version        - Update Java version in pom.xml (requires VERSION)
#   line-endings        - Fix line endings (CRLF to LF) for bash scripts
#   verify-dockerfiles  - Verify Dockerfiles have specific base image
#   verify-java         - Verify Java version in all pom.xml
#   list-services       - List all services
#
# Examples:
#   bash bulk-updates.sh dockerfiles "openjdk:11-jre-slim" "eclipse-temurin:11-jre-alpine"
#   bash bulk-updates.sh java-version 11
#   bash bulk-updates.sh line-endings
#   bash bulk-updates.sh verify-dockerfiles "eclipse-temurin:11-jre-alpine"
#   bash bulk-updates.sh verify-java 11
#
# Dependencies:
#   - sed installed
#   - find command available
#

set -e

ACTION="${1:-}"
ARG1="${2:-}"
ARG2="${3:-}"

case "$ACTION" in
  dockerfiles)
    if [ -z "$ARG1" ] || [ -z "$ARG2" ]; then
      echo "Usage: bash bulk-updates.sh dockerfiles <old-image> <new-image>"
      echo "Example: bash bulk-updates.sh dockerfiles 'openjdk:11-jre-slim' 'eclipse-temurin:11-jre-alpine'"
      exit 1
    fi
    echo "Updating all Dockerfiles: $ARG1 → $ARG2"
    find ts-*/ -name "Dockerfile" -exec sed -i "s|FROM $ARG1|FROM $ARG2|g" {} \;
    echo "Update complete. Verifying..."
    count=$(grep -r "FROM $ARG2" ts-*/Dockerfile | wc -l)
    echo "Found $count Dockerfiles with $ARG2"
    ;;
  
  pom-version)
    if [ -z "$ARG1" ] || [ -z "$ARG2" ]; then
      echo "Usage: bash bulk-updates.sh pom-version <old-version> <new-version>"
      echo "Example: bash bulk-updates.sh pom-version '0.8.1' '0.11.5'"
      exit 1
    fi
    echo "Updating all pom.xml: $ARG1 → $ARG2"
    find ts-*/ -name "pom.xml" -exec sed -i "s|<version>$ARG1</version>|<version>$ARG2</version>|g" {} \;
    echo "Update complete."
    ;;
  
  java-version)
    if [ -z "$ARG1" ]; then
      echo "Usage: bash bulk-updates.sh java-version <version>"
      echo "Example: bash bulk-updates.sh java-version 11"
      exit 1
    fi
    echo "Updating all pom.xml to Java $ARG1..."
    find ts-*/ -name "pom.xml" -exec sed -i "s|<java.version>.*</java.version>|<java.version>$ARG1</java.version>|g" {} \;
    echo "Update complete. Verifying..."
    count=$(grep -r "<java.version>$ARG1</java.version>" ts-*/pom.xml | wc -l)
    echo "Found $count pom.xml files with Java $ARG1"
    ;;
  
  line-endings)
    echo "Fixing line endings in bash scripts (CRLF → LF)..."
    find ./hack -name "*.sh" -exec sed -i 's/\r$//' {} \;
    find ./deployment -name "*.sh" -exec sed -i 's/\r$//' {} \; 2>/dev/null || true
    echo "Line endings fixed."
    echo "Verifying..."
    for file in ./hack/*.sh; do
      if file "$file" | grep -q CRLF; then
        echo "  ✗ $file (still has CRLF)"
      else
        echo "  ✓ $file (fixed)"
      fi
    done
    ;;
  
  verify-dockerfiles)
    if [ -z "$ARG1" ]; then
      echo "Usage: bash bulk-updates.sh verify-dockerfiles <image-name>"
      echo "Example: bash bulk-updates.sh verify-dockerfiles 'eclipse-temurin:11-jre-alpine'"
      exit 1
    fi
    echo "Verifying Dockerfiles use $ARG1..."
    count=$(grep -r "FROM $ARG1" ts-*/Dockerfile | wc -l)
    total=$(find ts-*/ -name "Dockerfile" | wc -l)
    echo "Found: $count / $total services"
    
    if [ "$count" -ne "$total" ]; then
      echo ""
      echo "Services missing the correct base image:"
      for dir in ts-*/; do
        if ! grep -q "FROM $ARG1" "$dir/Dockerfile" 2>/dev/null; then
          echo "  ✗ $dir"
        fi
      done
    else
      echo "✓ All services have the correct base image!"
    fi
    ;;
  
  verify-java)
    if [ -z "$ARG1" ]; then
      echo "Usage: bash bulk-updates.sh verify-java <version>"
      echo "Example: bash bulk-updates.sh verify-java 11"
      exit 1
    fi
    echo "Verifying Java version $ARG1 in all pom.xml..."
    count=$(grep -r "<java.version>$ARG1</java.version>" ts-*/pom.xml | wc -l)
    total=$(find ts-*/ -name "pom.xml" | wc -l)
    echo "Found: $count / $total services"
    
    if [ "$count" -ne "$total" ]; then
      echo ""
      echo "Services with incorrect Java version:"
      for dir in ts-*/; do
        if ! grep -q "<java.version>$ARG1</java.version>" "$dir/pom.xml" 2>/dev/null; then
          version=$(grep "<java.version>" "$dir/pom.xml" 2>/dev/null | sed 's/.*<java.version>\(.*\)<\/java.version>.*/\1/' | head -1)
          echo "  ✗ $dir (has: $version)"
        fi
      done
    else
      echo "✓ All services have Java $ARG1!"
    fi
    ;;
  
  list-services)
    echo "Train Ticket Services:"
    ls -d ts-*/ | sed 's|/||' | nl
    echo ""
    echo "Total: $(ls -d ts-*/ | wc -l) services"
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions:"
    echo "  dockerfiles      - Update all Dockerfiles"
    echo "  pom-version      - Update pom.xml versions"
    echo "  java-version     - Update Java version"
    echo "  line-endings     - Fix bash script line endings"
    echo "  verify-dockerfiles - Verify Dockerfile base image"
    echo "  verify-java      - Verify Java version"
    echo "  list-services    - List all services"
    exit 1
    ;;
esac
