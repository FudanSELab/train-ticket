---
name: developer
description: Implement changes, execute deployment tasks, fix infrastructure issues
argument-hint: [action] (bulk-update|build-stage|deploy-stage|configure|fix-issue)
---

---
name: developer
description: Implement changes, build and deploy stages, configure infrastructure
agentRole: implementation
applyTo:
  patterns:
    - "build*"
    - "deploy*"
    - "fix*"
    - "update*"
    - "configure*"
    - "change*"
    - "implement*"
---

# Developer Skill

Responsible for implementing changes, executing deployment tasks, and fixing infrastructure issues across all stages.

## Workflow: Bulk Update All Services

When to use: When a change needs to apply to all 41 microservices  
What it does:
1. Identifies all ts-*/ directories
2. Applies specified change pattern (regex or literal)
3. Verifies change in each file
4. Provides summary report
5. Tests changes compile or deploy correctly

Usage: Bulk update all services with [pattern]

Examples:
- Bulk update all services base image to eclipse-temurin:11-jre-alpine
- Bulk update all services environment variable [VAR] to [VALUE]
- Bulk update all services port to [PORT]

## Workflow: Build Stage

When to use: When preparing deployment to a stage (Docker, K8s, or AKS)  
What it does:
1. Execute build process for current stage
2. Generate required artifacts (images, manifests, configs)
3. Tag and version artifacts appropriately
4. Report build status
5. Identify any build failures

Usage: Build stage for [stage]  
Stages: docker, kubernetes, aks  
Example: Build stage for docker

## Workflow: Deploy Stage

When to use: When deploying to a stage (Docker, K8s, or AKS)  
What it does:
1. Execute deployment process for current stage
2. Apply configurations and manifests
3. Start services and verify startup
4. Report deployment status
5. Identify any deployment failures

Usage: Deploy stage to [stage]  
Stages: docker, kubernetes, aks  
Example: Deploy stage to docker

## Workflow: Configure Infrastructure

When to use: When setting up infrastructure for a stage  
What it does:
1. Create required configuration files
2. Apply default settings and policies
3. Configure networking and security
4. Set up monitoring and logging
5. Validate configuration

Usage: Configure [component] for [stage]  
Components: docker-compose, kubernetes, aks, networking, security, monitoring  
Example: Configure kubernetes networking for stage

## Workflow: Fix Issue

When to use: When addressing a blocker or configuration issue  
What it does:
1. Identify root cause of issue
2. Implement fix in code or configuration
3. Verify fix resolves issue
4. Test for side effects
5. Document fix and rationale

Usage: Fix [issue type] in [component]  
Issue types: deprecated-image, line-endings, version-mismatch, config-error  
Example: Fix deprecated-image in all Dockerfiles

## Task Templates

### Task: Update Configuration Across All Services

Objective: Update [CONFIG] from [OLD_VALUE] to [NEW_VALUE] in all services  
Scope: All 41 ts-*/ directories  
Files affected: [Dockerfile|pom.xml|deployment manifest]  
Find: [OLD_PATTERN]  
Replace: [NEW_PATTERN]  
Verification: [HOW TO VERIFY]  
Status: ___

### Task: Build and Deploy to Stage

Objective: Build and deploy to [STAGE] (docker|kubernetes|aks)  
Prerequisites: [REQUIREMENT 1], [REQUIREMENT 2]  
Steps:
  1. Run build process
  2. Verify artifacts created
  3. Execute deployment
  4. Check services healthy
  5. Verify access/connectivity

Blockers: ___  
Status: ___

### Task: Configure New Component

Objective: Configure [COMPONENT] for [STAGE]  
Requirements: [REQUIREMENT 1], [REQUIREMENT 2]  
Configuration files needed: [FILE 1], [FILE 2]  
Dependencies: [DEPENDENCY 1], [DEPENDENCY 2]  
Testing: [HOW TO TEST]  
Status: ___

## Implementation Checklist

Before starting any task:
- Confirm scope (single service vs. all 41)
- Verify current state with analyst skill
- Create backup if modifying critical files
- Document changes as you make them

After completing task:
- Run build or deployment verification
- Check for errors in logs
- Document outcome in task template
- Notify reviewer for checkpoint

## Stage-Specific Workflows

### Docker Stage

Build:
  1. mvn clean package -DskipTests
  2. ./hack/build-image.sh hdali00 train-ticket

Deploy:
  1. docker-compose up -d
  2. Wait for service startup
  3. Verify connectivity

Verify:
  1. docker-compose ps (all Up)
  2. docker-compose logs -f (no errors)
  3. curl http://localhost:8080 (responds)

### Kubernetes Stage

Build:
  1. Ensure images available (docker or registry)
  2. Prepare Kubernetes manifests
  3. Validate manifests (kubectl apply --dry-run)

Deploy:
  1. kubectl apply -f deployment/kubernetes-manifests/
  2. Wait for pod startup
  3. Verify service discovery

Verify:
  1. kubectl get pods (all Running)
  2. kubectl get svc (all endpoints active)
  3. kubectl logs [pod] (no errors)

### AKS Stage

Build:
  1. Create Azure Container Registry
  2. Build images in registry
  3. Prepare AKS manifests

Deploy:
  1. az aks create (provision cluster)
  2. kubectl apply -f manifests (deploy services)
  3. Configure ingress and networking

Verify:
  1. az aks show (cluster Running)
  2. kubectl get nodes (nodes Ready)
  3. kubectl get pods (services Running)

## Build & Deployment Scripts

Utility scripts are organized in `.github/scripts/developer/` for easy reuse.

### Maven Build Commands

For building with Maven, use:
```bash
bash .github/scripts/developer/maven-build.sh [action]
```

Available actions: `full-build`, `build`, `compile`, `package`, `single`, `tree`, `outdated`

Examples:
```bash
bash .github/scripts/developer/maven-build.sh build
bash .github/scripts/developer/maven-build.sh single ts-auth-service
bash .github/scripts/developer/maven-build.sh tree
```

### Docker Build & Push

For building Docker images, use:
```bash
bash .github/scripts/developer/docker-build.sh [action]
```

Available actions: `build-all`, `build-single`, `build-no-cache`, `push-single`, `push-all`, `cleanup`, `cleanup-dangling`

Examples:
```bash
bash .github/scripts/developer/docker-build.sh build-all
bash .github/scripts/developer/docker-build.sh push-all
bash .github/scripts/developer/docker-build.sh cleanup
```

### Docker Compose Operations

For managing docker-compose, use:
```bash
bash .github/scripts/developer/docker-compose.sh [action]
```

Available actions: `up`, `down`, `down-volumes`, `status`, `restart`, `restart-service`, `logs`, `logs-follow`, `logs-service`

Examples:
```bash
bash .github/scripts/developer/docker-compose.sh up
bash .github/scripts/developer/docker-compose.sh logs-follow
bash .github/scripts/developer/docker-compose.sh restart-service ts-auth-service
```

### Kubernetes Deployment

For Kubernetes deployments, use:
```bash
bash .github/scripts/developer/kubernetes-deploy.sh [action]
```

Available actions: `apply-all`, `dry-run`, `apply-single`, `restart`, `scale`, `delete`

Examples:
```bash
bash .github/scripts/developer/kubernetes-deploy.sh apply-all
bash .github/scripts/developer/kubernetes-deploy.sh dry-run
bash .github/scripts/developer/kubernetes-deploy.sh scale ts-auth-service 3
```

### Bulk Updates

For updating all 41 services at once, use:
```bash
bash .github/scripts/developer/bulk-updates.sh [action]
```

Available actions: `dockerfile-base-image`, `verify-base-image`, `pom-java-version`, `pom-version`, `docker-compose-env`, `check-services`, `missing-dockerfiles`

Examples:
```bash
bash .github/scripts/developer/bulk-updates.sh dockerfile-base-image
bash .github/scripts/developer/bulk-updates.sh verify-base-image
bash .github/scripts/developer/bulk-updates.sh check-services
```

### Troubleshooting & Fixing

For fixing issues and troubleshooting, use:
```bash
bash .github/scripts/developer/troubleshooting.sh [action]
```

Available actions: `check-port`, `kill-port`, `docker-logs`, `validate-compose`, `validate-k8s`, `fix-line-endings`, `fix-all-scripts`

Examples:
```bash
bash .github/scripts/developer/troubleshooting.sh check-port 8080
bash .github/scripts/developer/troubleshooting.sh fix-all-scripts
bash .github/scripts/developer/troubleshooting.sh validate-compose
```

All build and deployment scripts are in: `.github/scripts/developer/`

See `.github/scripts/README.md` for complete documentation.
