#!/bin/bash
#
# Script: maven-build.sh
# Purpose: Execute Maven build tasks for Train Ticket services
# Usage: bash maven-build.sh [action] [service]
#
# Actions:
#   clean-install     - Full build with tests (slowest)
#   install            - Install without clean
#   clean-package      - Package without tests (faster)
#   compile            - Just compile, no jar
#   dependency-tree    - Show dependency tree
#   check-updates      - Check for outdated dependencies
#   (default)          - clean-package (fastest build)
#
# Service (optional):
#   Leave empty to build all services
#   Or specify service name: ts-auth-service, ts-user-service, etc.
#
# Examples:
#   bash maven-build.sh                      # Build all, no tests
#   bash maven-build.sh clean-install        # Build all, with tests
#   bash maven-build.sh clean-package ts-auth-service
#   bash maven-build.sh compile
#
# Dependencies:
#   - Maven 3.11.0+
#   - Java 11+
#

set -e

ACTION="${1:-clean-package}"
SERVICE="${2:-}"

if [ ! -z "$SERVICE" ]; then
  echo "Building $SERVICE..."
  cd "$SERVICE"
else
  echo "Building all services..."
fi

case "$ACTION" in
  clean-install)
    echo "Running: mvn clean install"
    mvn clean install
    ;;
  
  install)
    echo "Running: mvn install"
    mvn install
    ;;
  
  clean-package)
    echo "Running: mvn clean package -DskipTests"
    mvn clean package -DskipTests
    ;;
  
  package)
    echo "Running: mvn package -DskipTests"
    mvn package -DskipTests
    ;;
  
  compile)
    echo "Running: mvn clean compile"
    mvn clean compile
    ;;
  
  compile-quick)
    echo "Running: mvn compile -q (quiet)"
    mvn compile -q
    ;;
  
  dependency-tree)
    echo "Running: mvn dependency:tree"
    mvn dependency:tree
    ;;
  
  check-updates)
    echo "Running: mvn versions:display-dependency-updates"
    mvn versions:display-dependency-updates
    ;;
  
  *)
    echo "Unknown action: $ACTION"
    echo "Available actions: clean-install, install, clean-package, package, compile, compile-quick, dependency-tree, check-updates"
    exit 1
    ;;
esac

echo "Build completed successfully!"
