---
name: train-ticket-orchestrator
description: Master orchestration agent that routes Train Ticket deployment tasks to the right sub-agent (Analyst, Developer, or Reviewer)
applyTo:
  patterns:
    - "deploy*"
    - "verify*"
    - "build*"
    - "test*"
    - "fix*"
    - "update*"
    - "session*"
    - "review*"
    - "checkpoint*"
    - "stage*"
---

# Train Ticket Deployment Orchestrator

Master agent that intelligently routes deployment tasks to the best sub-agent.

## How It Works

When you describe a deployment task, the orchestrator:
1. **Analyzes** what you're trying to accomplish
2. **Routes** to the specialist sub-agent
3. **Coordinates** work across multiple skills if needed

## Quick Start: Just Describe What You Want to Do

Instead of remembering "use analyst skill", just ask naturally:

```
I want to verify everything builds correctly
→ Routes to ANALYST agent

I need to update all 41 services with a new base image
→ Routes to DEVELOPER agent

Let me document what we completed today
→ Routes to REVIEWER agent

Is the docker stage ready for Kubernetes?
→ Routes to ANALYST agent to check
→ Then prompts REVIEWER if all quality gates pass
```

## Sub-Agents (Specialists)

### 1. Analyst Sub-Agent
**When orchestrator routes here**: You're asking about verification, testing, or status

Key indicators:
- "verify", "test", "check", "is it working", "status", "ready", "health"
- Questions about current state
- Validation requests
- Readiness checks

Example: "Is our docker build working?"

### 2. Developer Sub-Agent  
**When orchestrator routes here**: You need changes made or tasks implemented

Key indicators:
- "update", "fix", "build", "deploy", "configure", "change", "implement"
- Actionable requests
- Infrastructure changes
- Bulk updates across services

Example: "Update all Dockerfiles to use eclipse-temurin:11-jre-alpine"

### 3. Reviewer Sub-Agent
**When orchestrator routes here**: You need progress documented or handoff prepared

Key indicators:
- "session checkpoint", "review", "summarize", "document", "prepare", "relay", "completed"
- End-of-phase work
- Quality gate checks
- Handoff preparation

Example: "Prepare task relay for kubernetes stage"

## Common Workflows (Orchestrator Executes These)

### Workflow: Complete a Deployment Stage

```
User: "Complete the docker stage"

Orchestrator:
1. [ANALYST] Verify current build status
2. [DEVELOPER] Build docker images (if needed)
3. [ANALYST] Test docker deployment
4. [DEVELOPER] Fix any issues
5. [ANALYST] Verify stage is healthy
6. [REVIEWER] Check quality gates
7. [REVIEWER] Prepare relay to next stage
```

### Workflow: Fix a Blocking Issue

```
User: "There's a docker build error, fix it"

Orchestrator:
1. [ANALYST] Investigate the error
2. [DEVELOPER] Implement fix
3. [ANALYST] Verify fix works
4. [REVIEWER] Document the issue and solution
```

### Workflow: Session Handoff

```
User: "End of session, prepare handoff"

Orchestrator:
1. [ANALYST] Get current status
2. [REVIEWER] Document progress made
3. [REVIEWER] Update session relay
4. [ANALYST] Verify next steps are clear
```

## Benefits of This Approach

| Before | After |
|---|---|
| "Copilot, using analyst skill, verify build status" | "Verify the build" |
| "Copilot, using developer skill, update all Dockerfiles" | "Update all Dockerfiles to use eclipse-temurin:11-jre-alpine" |
| "Copilot, using reviewer skill, session checkpoint" | "Session checkpoint" |
| Manual context switching between 3 skills | Automatic routing to right specialist |
| Hard to coordinate multi-step workflows | Orchestrator chains skills automatically |

## Natural Language Examples

These all work without specifying which skill:

**Status checks:**
- "Is the build working?"
- "Are the docker services healthy?"
- "Can I deploy to kubernetes yet?"
- "What's the current deployment status?"

**Implementation:**
- "Build docker images"
- "Fix the deprecated base image"
- "Deploy to kubernetes"
- "Configure the docker-compose network"

**Documentation:**
- "Session checkpoint"
- "Prepare handoff to next stage"
- "Document what we accomplished"
- "Are we ready for AKS deployment?"

**Complex workflows (multi-step):**
- "Complete the docker stage"
- "Fix the build and verify it works"
- "Review docker readiness and prepare kubernetes"

## How Sub-Agents Link to Skills

Each sub-agent is defined in a skill file with YAML frontmatter:

| Sub-Agent | Skill File | Focus |
|---|---|---|
| Analyst | `.github/skills/analyst.md` | Verification, testing, health checks |
| Developer | `.github/skills/developer.md` | Implementation, changes, fixing |
| Reviewer | `.github/skills/reviewer.md` | Documentation, progress, handoff |

Sub-agents inherit all workflows and capabilities from their skill files.

## Routing Decision Tree

The orchestrator uses this logic to route:

```
User input:
  ├─ Status/Verification/Testing? → ANALYST
  ├─ Implementation/Build/Deploy/Fix? → DEVELOPER
  ├─ Document/Review/Handoff/Checkpoint? → REVIEWER
  ├─ Complex workflow (multi-step)? → Orchestrator chains them
  └─ Unclear? → Ask clarifying question
```

## Pro Tips for Efficiency

### Tip 1: Be Descriptive About What You Want
**Instead of**: "Fix the issue"  
**Say**: "The docker build is failing with deprecated base image, fix it across all 41 services"

→ Orchestrator knows exactly which sub-agent and what to do

### Tip 2: Describe Your Goal, Not the Tool
**Instead of**: "Using the developer skill, bulk update services"  
**Say**: "Update all service Dockerfiles to eclipse-temurin:11-jre-alpine"

→ Orchestrator routes automatically, you just describe the goal

### Tip 3: Use Natural Language for Workflows
**Instead of**: "Analyst verify, then Developer fix, then Analyst verify again"  
**Say**: "The build has errors, investigate and fix them"

→ Orchestrator chains the right steps automatically

### Tip 4: Request Stage Completions
**Instead of**: Three separate skill calls  
**Say**: "Complete the docker stage" or "Is kubernetes ready?"

→ Orchestrator runs the full workflow for you

### Tip 5: Session Structure
Start session with: "What's our current status?"  
During: "Fix [issues]", "Update [config]", etc.  
End with: "Session checkpoint"

→ Orchestrator maintains continuity throughout

## Context Propagation

When orchestrator routes between sub-agents:
1. Previous context is preserved
2. Session state is maintained
3. Each sub-agent has access to findings from others
4. Handoff notes are automatically carried forward

## Architecture Diagram

```
User Input
    ↓
Orchestrator (routing logic)
    ├→ ANALYST Sub-Agent → .github/skills/analyst.md
    ├→ DEVELOPER Sub-Agent → .github/skills/developer.md
    └→ REVIEWER Sub-Agent → .github/skills/reviewer.md
```

All three sub-agents can be invoked sequentially or in coordination, with the orchestrator managing the handoff and context.

## Reference Documentation

- **Deployment overview**: docs/deployment.md
- **Current status**: docs/session_relay.md
- **File navigation**: docs/context_guide.md
- **Analyst workflows**: .github/skills/analyst.md
- **Developer workflows**: .github/skills/developer.md
- **Reviewer workflows**: .github/skills/reviewer.md
