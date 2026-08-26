---
name: analyst
description: Analyze deployment state, verify readiness, identify issues and blockers
argument-hint: [action] (verify-build|test-deployment|validate-config|find-issues|stage-readiness)
---

---
name: analyst
description: Verify deployment state, test changes, identify issues across all stages
agentRole: verification
applyTo:
  patterns:
    - "verify*"
    - "test*"
    - "check*"
    - "status*"
    - "ready*"
    - "health*"
    - "is the*"
---

# Analyst Skill

Responsible for analyzing deployment state, verifying readiness, and identifying issues at any stage.

## Workflow: Verify Build Status

When to use: After making code or configuration changes  
What it does:
1. Run mvn clean install -DskipTests
2. Check for compilation errors
3. List services that compiled successfully
4. Identify any failures
5. Report build health status

Usage: Verify build status

Expected output: BUILD SUCCESS with all 41 services compiled

## Workflow: Test Deployment

When to use: Before attempting deployment to next stage  
What it does:
1. Validate configuration files for current stage
2. Test critical service locally
3. Report success or error
4. Identify configuration issues
5. Provide troubleshooting steps

Usage: Test deployment for [stage]  
Stages: docker, kubernetes, aks  
Example: Test deployment for docker stage

## Workflow: Stage Readiness Check

When to use: Before moving to next deployment stage  
What it does:
1. Verify all services from current stage are healthy
2. Check all artifacts are ready (images, manifests, configs)
3. Validate networking and dependencies
4. Identify any blockers to progression
5. Generate stage readiness report

Usage: Check stage readiness for [stage]  
Stages: docker, kubernetes, aks  
Example: Check stage readiness for docker

## Investigation Queries

### Find Configuration Issues

Look for:
- Version mismatches across services
- Invalid configuration values
- Missing required files or settings
- Deprecated or unsupported versions

Report:
- Issue description
- Affected services or files
- Severity level
- Recommended fix

### What is the Status of Current Deployment?

Check:
- Current stage status
- Running services/containers/pods
- Health of databases and infrastructure
- Any error conditions
- Performance metrics

### Are there any Deployment Blockers?

Search for:
- Services that failed to start
- Configuration errors
- Missing dependencies
- Network connectivity issues
- Resource constraints

Report findings with:
- Root cause analysis
- Affected components
- Recommended actions

## Deployment Checkpoints

### Maven Build Success

Criteria:
- mvn clean install runs without errors
- All 41 services compile to target/classes
- No compiler warnings
- Jar files created for all services

Verification: mvn clean install -DskipTests
Expected: BUILD SUCCESS after 10 min

### Docker Stage Ready

Criteria:
- All 41 Dockerfile build successfully
- Base image is eclipse-temurin:11-jre-alpine
- Images tagged as hdali00/ts-*:train-ticket
- docker-compose.yml is valid

Verification: docker-compose up -d
Expected: All services running, no errors

### Docker Services Healthy

Criteria:
- All 41 service containers running
- MongoDB and MySQL containers healthy
- Redis cache responding
- Services can communicate
- Web UI responds at http://localhost:8080

Verification:
- docker-compose ps (all containers Up)
- docker-compose logs (no critical errors)
- curl http://localhost:8080 (returns valid response)

### Kubernetes Deployment Ready

Criteria:
- K8s cluster is running
- All manifests are valid YAML
- Images are available in registry
- Persistent volumes configured
- Network policies defined

Verification: kubectl apply --dry-run=client -f deployment/
Expected: No validation errors

### Kubernetes Services Running

Criteria:
- All 41 pods running successfully
- Services can discover each other
- Persistent volumes mounted
- Ingress routing traffic correctly
- No pod errors in logs

Verification:
- kubectl get pods (all Running)
- kubectl get svc (all endpoints active)
- kubectl logs [pod] (no errors)

### AKS Deployment Ready

Criteria:
- AKS cluster provisioned
- Azure Container Registry accessible
- Images pushed to registry
- Networking configured
- Identity and access configured

Verification: az aks show --name [cluster]
Expected: Cluster in Succeeded state

### Application Ready for Production

Criteria:
- All services running and healthy
- User can access web UI
- Services communicate correctly
- Databases responding to queries
- Monitoring and logging enabled
- Auto-scaling policies active
- No error logs in output

Verification:
- Application accessible and responsive
- All service endpoints functional
- Database queries successful
- Monitoring dashboard populated
- Metrics and logs being collected

## Verification Scripts

Utility scripts are organized in `.github/scripts/analyst/` for easy reuse:

### Docker Stage Checks

For Docker container and image verification, use:
```bash
bash .github/scripts/analyst/docker-checks.sh [action]
```

Available actions: `status`, `logs`, `logs-follow`, `inspect`, `resources`, `count`, `images`, `network`

Examples:
```bash
bash .github/scripts/analyst/docker-checks.sh status
bash .github/scripts/analyst/docker-checks.sh logs
bash .github/scripts/analyst/docker-checks.sh resources
```

### Kubernetes Pod Checks

For Kubernetes pod and service verification, use:
```bash
bash .github/scripts/analyst/kubernetes-checks.sh [action]
```

Available actions: `status`, `wide`, `describe`, `logs`, `events`, `services`, `nodes`, `resources`

Examples:
```bash
bash .github/scripts/analyst/kubernetes-checks.sh status
bash .github/scripts/analyst/kubernetes-checks.sh resources
bash .github/scripts/analyst/kubernetes-checks.sh services
```

### System Health Checks

For Java, Maven, and system health verification, use:
```bash
bash .github/scripts/analyst/general-health.sh [check]
```

Available checks: `java`, `maven`, `docker`, `kubectl`, `ports`, `disk`, `memory`, `all`

Examples:
```bash
bash .github/scripts/analyst/general-health.sh all
bash .github/scripts/analyst/general-health.sh docker
bash .github/scripts/analyst/general-health.sh ports
```

All verification scripts are in: `.github/scripts/analyst/`

See `.github/scripts/README.md` for complete documentation.
