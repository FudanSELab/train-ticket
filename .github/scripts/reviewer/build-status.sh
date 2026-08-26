#!/bin/bash
#
# Script: build-status.sh
# Purpose: Report Maven build status for Train Ticket services
# Usage: bash build-status.sh [action]
#
# Actions:
#   full-build     - Run full Maven build and report
#   compile        - Run compile only and report status
#   errors         - Show compilation errors
#   count          - Count compiled vs total services
#   check-failed   - List services that failed to compile
#   (default)      - Quick build status check
#
# Examples:
#   bash build-status.sh
#   bash build-status.sh full-build
#   bash build-status.sh compile
#   bash build-status.sh errors
#   bash build-status.sh check-failed
#
# Dependencies:
#   - Maven 3.11.0+
#   - Java 11+
#

set -e

ACTION="${1:-check}"

case "$ACTION" in
  full-build)
    echo "=== Running Full Maven Build ==="
    echo "Start time: $(date)"
    
    if mvn clean install -DskipTests; then
      echo ""
      echo "=== BUILD PASSED ==="
      echo "All services compiled successfully"
    else
      echo ""
      echo "=== BUILD FAILED ==="
      echo "Some services failed to compile. Run 'bash build-status.sh errors' for details."
      exit 1
    fi
    
    echo "End time: $(date)"
    ;;
  
  compile)
    echo "=== Running Maven Compile ==="
    if mvn clean compile -q; then
      echo "✓ Compilation successful"
    else
      echo "✗ Compilation failed"
      exit 1
    fi
    ;;
  
  errors)
    echo "=== Compilation Errors ==="
    mvn clean compile 2>&1 | grep -E "ERROR|error" | head -20 || echo "No errors found"
    ;;
  
  count)
    echo "=== Service Compilation Status ==="
    total=$(ls -d ts-*/ 2>/dev/null | wc -l)
    compiled=$(find ts-*/ -name "classes" -type d 2>/dev/null | wc -l)
    echo "Total services: $total"
    echo "Compiled services: $compiled"
    echo "Status: $compiled / $total"
    
    if [ "$compiled" -eq "$total" ]; then
      echo "✓ All services compiled!"
    else
      echo "✗ $(($total - $compiled)) services not compiled"
    fi
    ;;
  
  check-failed)
    echo "=== Checking Service Compilation ==="
    failed_count=0
    
    for dir in ts-*/; do
      if ! (cd "$dir" && mvn compile -q 2>/dev/null); then
        echo "✗ FAILED: $dir"
        ((failed_count++))
      fi
    done
    
    if [ $failed_count -eq 0 ]; then
      echo "✓ All services compiled successfully!"
    else
      echo ""
      echo "Failed: $failed_count services"
    fi
    ;;
  
  check)
    echo "=== Build Status Check ==="
    if mvn clean compile -q 2>/dev/null; then
      echo "✓ BUILD PASSED"
    else
      echo "✗ BUILD FAILED"
      exit 1
    fi
    
    echo "Compiled services: $(find ts-*/ -name 'classes' -type d 2>/dev/null | wc -l) / $(ls -d ts-*/ 2>/dev/null | wc -l)"
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: full-build, compile, errors, count, check-failed, check"
    exit 1
    ;;
esac
