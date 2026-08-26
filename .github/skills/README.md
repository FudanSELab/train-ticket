---
name: train-ticket-skills
description: Three generic skills for coordinating Train Ticket deployment across Docker, Kubernetes, and AKS
---

# Train Ticket Skills

Three generic skills for managing deployment across all stages (Docker > Kubernetes > AKS):

## 1. Analyst Skill (.github/skills/analyst.md)

Responsibilities: Analyze deployment state, verify readiness, identify issues

Use when you need to:
- Verify the build is healthy after changes
- Test deployment to current stage
- Validate configuration
- Check stage readiness for progression
- Investigate specific issues

Key workflows:
- Verify build status
- Test deployment
- Stage readiness check

Investigation queries:
- Find configuration issues
- What is the status of current deployment?
- Are there any deployment blockers?

Example usage:
```
Copilot, verify build status
Copilot, test deployment for docker stage
Copilot, check stage readiness for kubernetes
Copilot, using analyst skill, find all configuration issues
```

---

## 2. Developer Skill (.github/skills/developer.md)

Responsibilities: Implement changes, execute deployment tasks, fix infrastructure

Use when you need to:
- Update configuration across 41 services
- Build and deploy to a stage
- Configure infrastructure
- Fix blockers and infrastructure issues
- Execute bulk changes

Key workflows:
- Bulk update all services
- Build stage
- Deploy stage
- Configure infrastructure
- Fix issue

Stage-specific workflows:
- Docker: Build images, deploy with docker-compose
- Kubernetes: Apply manifests, configure networking
- AKS: Provision cluster, configure Azure resources

Example usage:
```
Copilot, bulk update all services base image to eclipse-temurin:11-jre-alpine
Copilot, build stage for docker
Copilot, deploy stage to kubernetes
Copilot, fix deprecated-image in all Dockerfiles
Copilot, using developer skill, configure kubernetes networking for stage
```

---

## 3. Reviewer Skill (.github/skills/reviewer.md)

Responsibilities: Review progress, document changes, prepare handoff

Use when you need to:
- Summarize session work at end of day
- Review stage completion
- Document blockers and solutions
- Prepare handoff for next session or stage
- Verify quality gates

Key workflows:
- Session checkpoint
- Stage review
- Prepare task relay
- Quality gates
- Progress update

Deliverables:
- Updated docs/session_relay.md
- Session log entries
- Progress tracking table
- Quality gate verification
- Stage transition approval

Example usage:
```
Copilot, session checkpoint
Copilot, review docker stage completion
Copilot, prepare task relay for kubernetes stage
Copilot, check quality gates for docker stage
Copilot, using reviewer skill, update progress tracking
```

---

## Workflow Pattern: Complete Stage Deployment

1. **Analyst** → Verify current stage readiness
2. **Developer** → Implement required changes
3. **Analyst** → Test changes and verify
4. **Reviewer** → Document progress and stage completion
5. **Analyst** → Check readiness to advance to next stage

---

## Quick Reference

| When you have this question | Use this skill | Example |
|---|---|---|
| Does the build still work? | Analyst | `verify build status` |
| Is the current stage working? | Analyst | `test deployment for docker` |
| How do I update all 41 services? | Developer | `bulk update all services` |
| How do I deploy to a stage? | Developer | `deploy stage to kubernetes` |
| Are we ready for the next stage? | Reviewer | `review kubernetes completion` |
| What do I do next session? | Reviewer | `prepare task relay` |
| Is our code/config quality good? | Reviewer | `check quality gates for docker` |
| What was completed this session? | Reviewer | `session checkpoint` |

---

## Deployment Stages

All three skills work across these stages:

### Stage 1: Docker
- Build Docker images for 41 services
- Deploy with docker-compose locally
- Verify services running and communicating

### Stage 2: Kubernetes  
- Set up local K8s cluster
- Deploy Kubernetes manifests
- Configure networking and persistence

### Stage 3: AKS
- Provision AKS cluster in Azure
- Configure Azure Container Registry
- Deploy to production-ready AKS

---

## Skill Discovery

All skills are in: `.github/skills/`

To reference a skill: "Copilot, using [skill-name] skill, [your task]"

Examples:
- Copilot, using analyst skill, test deployment for docker stage
- Copilot, using developer skill, build stage for kubernetes
- Copilot, using reviewer skill, review docker stage completion

---

## How to Use These Skills in Coordination

Example: Completing Docker Stage

1. Analyst: Verify build status
   ```
   Copilot, verify build status
   ```

2. Developer: Build Docker images
   ```
   Copilot, build stage for docker
   ```

3. Analyst: Test the deployment
   ```
   Copilot, test deployment for docker stage
   ```

4. Developer: Fix any issues
   ```
   Copilot, fix [issue] in [component]
   ```

5. Reviewer: Check readiness to move to Kubernetes
   ```
   Copilot, review docker stage completion
   Copilot, check quality gates for docker stage
   ```

6. Reviewer: Prepare handoff to Kubernetes stage
   ```
   Copilot, prepare task relay
   ```
