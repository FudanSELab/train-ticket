---
name: train-ticket-scripts
description: Utility scripts organized by skill for Docker, Kubernetes, and deployment verification
---

# Train Ticket Utility Scripts

Reusable scripts organized by deployment skill (Analyst, Developer, Reviewer).

## Directory Structure

```
.github/scripts/
├── README.md                 (this file - script reference)
├── analyst/
│   ├── docker-checks.sh      (Docker container and image verification)
│   ├── kubernetes-checks.sh  (Kubernetes pod and service checks)
│   └── general-health.sh     (Maven, Java, and system health)
├── developer/
│   ├── maven-build.sh        (Maven compile and package commands)
│   ├── docker-build.sh       (Docker image building)
│   ├── docker-compose.sh     (docker-compose operations)
│   ├── kubernetes-deploy.sh  (Kubernetes manifest deployment)
│   ├── bulk-updates.sh       (Bulk file updates for 41 services)
│   └── troubleshooting.sh    (Port checking and cleanup)
└── reviewer/
    ├── build-status.sh       (Maven and build verification)
    ├── progress-summary.sh   (Docker, K8s, and service counts)
    ├── health-reporting.sh   (Critical service testing)
    └── checklist.sh          (Session and quality gate checklists)
```

## Quick Reference

### When to Use Each Script Directory

**Analyst Scripts** (.github/scripts/analyst/)
- Verify current deployment state
- Check Docker container health
- Check Kubernetes pod status
- Verify system requirements
- Test service connectivity

**Developer Scripts** (.github/scripts/developer/)
- Build Java services with Maven
- Build Docker images
- Deploy with docker-compose
- Deploy to Kubernetes
- Update configurations across services
- Fix issues and troubleshoot

**Reviewer Scripts** (.github/scripts/reviewer/)
- Generate build status reports
- Track deployment progress
- Verify service health
- Generate session checklists
- Create progress summaries

## How Skills Use These Scripts

Each skill file references the appropriate scripts:

**Analyst Skill** → Uses `.github/scripts/analyst/`
- When verifying build status
- When testing deployment
- When checking stage readiness

**Developer Skill** → Uses `.github/scripts/developer/`
- When building stage
- When deploying stage
- When bulk updating services
- When fixing issues

**Reviewer Skill** → Uses `.github/scripts/reviewer/`
- When doing session checkpoint
- When reviewing stage completion
- When preparing task relay
- When checking quality gates

## Script Execution Pattern

All scripts follow this pattern:

```bash
# Source the script or execute directly
source .github/scripts/analyst/docker-checks.sh
# or
bash .github/scripts/analyst/docker-checks.sh

# Scripts are organized as functions or executable commands
# Some scripts need arguments, see script header for usage
```

## Common Usage Examples

### From Analyst Workflow

```bash
# Check Docker status
bash .github/scripts/analyst/docker-checks.sh

# Check Kubernetes pods
bash .github/scripts/analyst/kubernetes-checks.sh

# Verify system health
bash .github/scripts/analyst/general-health.sh
```

### From Developer Workflow

```bash
# Build all services with Maven
bash .github/scripts/developer/maven-build.sh

# Build Docker images
bash .github/scripts/developer/docker-build.sh

# Deploy to docker-compose
bash .github/scripts/developer/docker-compose.sh up

# Bulk update all Dockerfiles
bash .github/scripts/developer/bulk-updates.sh dockerfiles eclipse-temurin:11-jre-alpine
```

### From Reviewer Workflow

```bash
# Get build status
bash .github/scripts/reviewer/build-status.sh

# Generate progress summary
bash .github/scripts/reviewer/progress-summary.sh

# Run health checks
bash .github/scripts/reviewer/health-reporting.sh

# Generate session checklist
bash .github/scripts/reviewer/checklist.sh
```

## Script Categories

### Analyst Scripts

**docker-checks.sh**
- List running containers
- Check docker-compose status
- View container logs
- Inspect containers
- Check container resource usage
- Verify service connectivity
- List Docker images

**kubernetes-checks.sh**
- List pods by status
- Get pod details
- View pod logs
- Check pod events
- Port forward to pods
- Execute commands in pods
- Check service endpoints
- List services

**general-health.sh**
- Verify Maven, Java, Docker, Kubectl versions
- Check critical services
- Test MongoDB connectivity
- Test MySQL connectivity
- Test Redis connectivity

### Developer Scripts

**maven-build.sh**
- Clean build (with tests)
- Build without tests (faster)
- Just compile
- Package only
- Build specific service
- Show dependency tree
- Check for outdated dependencies

**docker-build.sh**
- Build single image
- Build with no cache
- Push single image
- Push all images
- Clean up images
- Clean up dangling images

**docker-compose.sh**
- Start services
- Stop services
- Restart services
- View status
- View logs
- Tail logs

**kubernetes-deploy.sh**
- Apply all manifests
- Dry-run validation
- Apply specific manifest
- Restart deployment
- Scale deployment
- Delete deployment

**bulk-updates.sh**
- Update all Dockerfiles
- Update all pom.xml
- Update docker-compose
- Fix line endings
- Add environment variables

**troubleshooting.sh**
- Check port usage
- Kill process on port
- Check Docker daemon logs
- Validate configurations
- Get detailed error info

### Reviewer Scripts

**build-status.sh**
- Maven build status
- Compilation errors
- Count compiled services
- Services that failed

**progress-summary.sh**
- Total services count
- Docker images count
- Running containers count
- Running pods count
- Java 11 verification
- Dockerfile verification

**health-reporting.sh**
- Generate health check report
- Test critical services
- Check database connectivity
- List recent file changes

**checklist.sh**
- Session checklist
- Quality gate checklist
- Code quality checks
- Configuration checks

## Adding New Scripts

When adding a new script:

1. Choose appropriate directory (analyst, developer, or reviewer)
2. Follow naming convention: `purpose-specific.sh`
3. Add script header with usage documentation
4. Make script executable: `chmod +x script.sh`
5. Reference in corresponding skill file
6. Update this README with description

## Script Header Template

Every script should start with:

```bash
#!/bin/bash
#
# Script: [script-name]
# Purpose: [What this script does]
# Usage: bash [script-name].sh [ARGS]
# 
# Arguments:
#   ARG1 - Description
#   ARG2 - Description
#
# Examples:
#   bash [script-name].sh arg1 arg2
#
# Dependencies:
#   - Docker (for docker-related scripts)
#   - kubectl (for kubernetes scripts)
#   - Maven (for maven scripts)
#

set -e  # Exit on error
```

## Cross-Platform Considerations

Scripts are written for **bash/Linux/Mac**. For Windows:
- Use WSL (Windows Subsystem for Linux)
- Or convert specific commands for PowerShell
- Line endings must be LF (Unix style)

To fix line endings:
```bash
sed -i 's/\r$//' .github/scripts/*/*.sh
```

## Performance Notes

Some scripts (like bulk updates) iterate over all 41 services. Typical performance:
- Maven compile: ~10 minutes
- Docker build all: ~15 minutes
- Kubernetes manifest deployment: ~5 minutes
- Bulk file updates: ~1 minute

## Related Documentation

- **Analyst Skill**: .github/skills/analyst.md (references analyst scripts)
- **Developer Skill**: .github/skills/developer.md (references developer scripts)
- **Reviewer Skill**: .github/skills/reviewer.md (references reviewer scripts)
- **Orchestrator Agent**: .github/agents/AGENTS.md (routes to appropriate skill/scripts)
- **Deployment Guide**: docs/deployment.md
- **Session Relay**: docs/session_relay.md
