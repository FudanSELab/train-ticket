---
name: reviewer
description: Review deployment progress, document changes, prepare handoff to next session or stage
argument-hint: [action] (session-checkpoint|stage-review|prepare-relay|quality-gates|progress-update)
---

---
name: reviewer
description: Review progress, document changes, prepare handoff between stages
agentRole: documentation
applyTo:
  patterns:
    - "session*"
    - "checkpoint*"
    - "review*"
    - "summarize*"
    - "document*"
    - "prepare*"
    - "relay*"
    - "completed*"
---

# Reviewer Skill

Responsible for reviewing deployment progress, documenting changes, and preparing handoff to next session or stage.

## Workflow: Session Checkpoint

When to use: At the end of each working session  
What it does:
1. Check which files were modified
2. Run mvn clean compile to verify build status
3. List any remaining blockers
4. Update docs/session_relay.md with progress
5. Summarize work completed

Usage: Session checkpoint

Output: Updated docs/session_relay.md with completed work and next steps

## Workflow: Stage Review

When to use: When completing work for a deployment stage  
What it does:
1. Verify all stage objectives completed
2. Check all artifacts generated
3. Confirm all tests passed
4. Document stage completion
5. Identify readiness for next stage

Usage: Review [stage] completion  
Stages: docker, kubernetes, aks  
Example: Review docker stage completion

## Workflow: Prepare Task Relay

When to use: Preparing handoff document for next session  
What it does:
1. Document current objective and stage
2. Identify and explain current blockers with solutions
3. List what has already been completed (avoid duplication)
4. Define exact next immediate steps
5. Update docs/session_relay.md with session log
6. Note any surprises or learnings
7. Update progress tracking table

Usage: Prepare task relay

Output location: docs/session_relay.md

Content template:
- Current Objective: What are we trying to deploy?
- Current Phase: Which stage are we in?
- The Problem: What is blocking us? (with root cause and solution)
- What is Already Done: Don't repeat this work!
- Next Immediate Steps: Exactly what to do (step by step)
- Progress Tracking: Table of what's completed vs. pending
- Session Log: Who did what when
- Definition of Done: Success criteria for this phase

## Workflow: Quality Gates

When to use: Before approving work for next stage  
What it does:
1. Verify all quality criteria met
2. Check for technical debt or issues
3. Validate documentation is complete
4. Confirm tests passing
5. Review code and configuration changes

Usage: Check quality gates for [stage]  
Stages: docker, kubernetes, aks  
Example: Check quality gates for docker stage

## Review Checklist

Before each session ends, verify:
- Session summary written in docs/session_relay.md
- All code and configuration changes documented
- Build verification completed (mvn clean compile)
- Any new blockers documented with root cause
- Next steps clearly defined for following session
- Learnings and patterns recorded for future reference
- Progress tracking table updated
- Quality gates verified

## Progress Tracking Template

| Task | Status | Owner | Notes |
|------|--------|-------|-------|
| Java upgrade to Java 11 | DONE | [Previous] | All pom.xml updated |
| Maven build verification | DONE | [Previous] | Successful compilation |
| Dockerfile updates | DONE | [Previous] | Ready for image build |
| Bash script fixes | TODO | [Current] | Critical for WSL |
| Docker image build | TODO | [Current] | Blocked on base image fix |
| Docker local deploy | TODO | [Current] | Depends on images ready |
| Push to registry | TODO | [Next] | After Docker verified |
| Local K8s deploy | TODO | [Future] | Stage 2 |
| AKS provisioning | TODO | [Future] | Stage 3 |

## Session Log Entry Template

Session Date: [YYYY-MM-DD]  
Duration: [X hours]  
Participant: [Name/Role]  
Stage: [docker|kubernetes|aks]

Completed:
- [Task 1]
- [Task 2]
- [Task 3]

Blockers Found:
- [Issue 1] - Root cause: [X] - Solution: [Y]
- [Issue 2] - Root cause: [X] - Solution: [Y]

Learnings:
- [Pattern 1 discovered]
- [Best practice found]
- [Gotcha encountered]

Next Session Should:
1. [Step 1]
2. [Step 2]
3. [Step 3]

Handoff Quality:
- Documentation: GOOD|FAIR|NEEDS-WORK
- Code quality: GOOD|FAIR|NEEDS-WORK
- Clarity of next steps: CLEAR|SOME-AMBIGUITY|UNCLEAR

## Definition of Done - Session Level

Work is ready to pass to next session when:
- All code and config changes tested and verified
- No broken builds in pipeline
- All changes documented in code comments
- Blockers identified with proposed solutions
- docs/session_relay.md updated with current state
- Next immediate steps crystal clear and actionable
- No ambiguity about what was done or why
- Progress tracking table updated
- Session log entry complete

## Definition of Done - Stage Level

Work is ready to pass to next stage when:
- All 41 services deployed and running
- All services communicate correctly
- All databases connected and operational
- All tests passing (or documented as skipped)
- Monitoring and logging configured
- Security policies applied
- Performance requirements met
- Documentation complete for this stage
- Rollback procedures documented
- Approval from team lead obtained

## Quality Gates Checklist

Before marking work complete:

Code Quality:
- Code compiles without errors
- No new warnings introduced
- No commented-out code left behind
- Proper error handling implemented
- Security best practices followed

Configuration Quality:
- Configuration files validated
- Environment variables properly set
- Secrets managed securely
- File permissions correct
- Paths use correct separators

Testing Quality:
- All automated tests pass
- Manual testing completed
- Edge cases tested
- Rollback procedures tested
- Performance tested

Documentation Quality:
- Changes documented clearly
- Instructions provided
- Known issues listed
- Troubleshooting guide updated
- Architecture diagrams current

Deployment Quality:
- Deployment verified in target environment
- Services running without errors
- No resource constraints
- Networking correct
- Logging and monitoring working

## Stage Transition Criteria

Before moving from Docker to Kubernetes:
- All 41 services running in Docker
- Web UI accessible and functional
- All services communicating
- Docker images pushed to registry
- docker-compose deployment verified stable

Before moving from Kubernetes to AKS:
- All 41 services running in K8s locally
- Service discovery working
- Persistent storage working
- Networking and ingress configured
- Monitoring and logging active
- K8s manifests documented and versioned

Before moving to Production AKS:
- All previous stages verified
- Security audit complete
- Performance requirements met
- Disaster recovery tested
- Team trained on deployment
- Runbooks written for common tasks

## Progress Tracking & Reporting Scripts

Utility scripts for tracking progress and generating reports are organized in `.github/scripts/reviewer/`.

### Build Status Reporting

For Maven build verification, use:
```bash
bash .github/scripts/reviewer/build-status.sh [action]
```

Available actions: `status`, `build-time`, `errors`, `count-services`, `count-compiled`, `failed-services`

Examples:
```bash
bash .github/scripts/reviewer/build-status.sh status
bash .github/scripts/reviewer/build-status.sh count-compiled
bash .github/scripts/reviewer/build-status.sh failed-services
```

### Progress & Health Reporting

For deployment progress reporting, use:
```bash
bash .github/scripts/reviewer/progress-summary.sh [action]
```

Available actions: `docker-status`, `docker-images`, `docker-running`, `docker-not-running`, `docker-disk-usage`, `k8s-status`, `k8s-running`, `k8s-not-running`, `k8s-resources`, `full-summary`

Examples:
```bash
bash .github/scripts/reviewer/progress-summary.sh full-summary
bash .github/scripts/reviewer/progress-summary.sh docker-running
bash .github/scripts/reviewer/progress-summary.sh k8s-status
```

### Health & Quality Checks

For testing and validating critical components, use:
```bash
bash .github/scripts/reviewer/health-reporting.sh [action]
```

Available actions: `tools-check`, `critical-services`, `todos`, `fixmes`, `debug-logging`, `credentials`, `build-errors`, `deprecated-deps`, `version-conflicts`

Examples:
```bash
bash .github/scripts/reviewer/health-reporting.sh tools-check
bash .github/scripts/reviewer/health-reporting.sh critical-services
bash .github/scripts/reviewer/health-reporting.sh build-errors
```

### Session & Quality Checklists

For generating checklists and documentation, use:
```bash
bash .github/scripts/reviewer/checklist.sh [action]
```

Available actions: `session-checklist`, `quality-gates`, `stage-transition`, `progress-table`, `session-log`, `status-report`

Examples:
```bash
bash .github/scripts/reviewer/checklist.sh session-checklist
bash .github/scripts/reviewer/checklist.sh quality-gates
bash .github/scripts/reviewer/checklist.sh progress-table
```

All progress tracking and reporting scripts are in: `.github/scripts/reviewer/`

See `.github/scripts/README.md` for complete documentation.
