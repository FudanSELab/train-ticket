---
name: context-guide
description: Navigation guide for Train Ticket deployment documentation
---

# Documentation Navigation Guide

This directory contains documentation for coordinating Train Ticket deployment across Docker, Kubernetes, and AKS.

Quick Navigation

New to this deployment?
  Start with docs/deployment.md

Coming back from a previous session?
  Read docs/session_relay.md first

Need technical details for current stage?
  Check docs/docker_stage.md, docs/kubernetes_stage.md, or docs/aks_stage.md

Ready to use skills?
  Review .github/skills/README.md

File Descriptions

### docs/deployment.md

What it contains: Project overview, deployment stages, and architecture
Best for: Understanding overall deployment strategy and current status
Key sections:
  - Deployment Stages (Docker > K8s > AKS)
  - Architecture overview
  - Key configuration files
  - Current status by stage
  - Success criteria

When to read:
  - Starting work on this project
  - Need overall strategy
  - Understanding deployment phases
  - Planning next steps

### docs/session_relay.md

What it contains: Current state and handoff to next session
Best for: Picking up work exactly where it left off
Key sections:
  - Current Objective (what we're doing)
  - The Problem (what's blocking)
  - What is Already Done (don't repeat)
  - Next Immediate Steps (exactly what to do)
  - Progress Tracking table
  - Session Log
  - Definition of Done

When to read:
  - Resuming work after a break
  - Need immediate next steps
  - Want to avoid duplicate effort
  - Coming back after another session worked on it

### docs/docker-local-startup.md

What it contains: Step-by-step guide to starting services via Docker locally
Best for: Actually starting all 41 services on your machine
Key sections:
  - Prerequisites and verification
  - Quick start (3 steps)
  - Detailed build and deployment steps
  - Health verification
  - Stopping and cleanup
  - Common issues and troubleshooting
  - Performance tuning

When to read:
  - Ready to start services locally
  - Running Docker Compose for first time
  - Debugging local Docker issues
  - Need exact commands to run

### docs/docker_stage.md

What it contains: Docker local deployment details (architecture and strategy)
Best for: Understanding Stage 1 (Docker) conceptually
Key sections:
  - Docker Compose configuration
  - Service dependencies
  - Build process
  - Testing and verification
  - Troubleshooting

When to read:
  - Currently in Docker deployment stage
  - Need architectural understanding
  - Debugging Docker configuration issues

### docs/kubernetes_stage.md

What it contains: Local Kubernetes deployment details
Best for: Understanding and implementing Stage 2 (Local K8s)
Key sections:
  - Kubernetes manifest structure
  - Service discovery configuration
  - Persistent volume setup
  - Networking and ingress
  - Testing and verification

When to read:
  - Moving to local Kubernetes deployment
  - Setting up K8s cluster
  - Debugging Kubernetes issues

### docs/aks_stage.md

What it contains: Azure AKS deployment details
Best for: Understanding and implementing Stage 3 (AKS)
Key sections:
  - AKS cluster provisioning
  - Azure Container Registry setup
  - Identity and access management
  - Networking and security
  - Monitoring and auto-scaling

When to read:
  - Ready for AKS deployment
  - Configuring Azure infrastructure
  - Debugging AKS issues

### .github/skills/README.md

What it contains: Skills overview and quick reference
Best for: Understanding available skills and how to use them
Key sections:
  - Three skills: analyst, developer, reviewer
  - When to use each skill
  - Example usage patterns
  - Quick reference table

When to read:
  - Learning how to use skills
  - Need to know which skill applies
  - Looking for example commands

How Files Work Together

Starting a Session:
  1. Read docs/session_relay.md (2 min) → Understand current state
  2. Check docs/deployment.md (2 min) → Understand overall strategy
  3. Read stage-specific doc (docker/kubernetes/aks) → Details for current work
  4. Use appropriate skill → Choose analyst, developer, or reviewer

Completing Work:
  1. Use developer skill → Implement changes
  2. Use analyst skill → Test and verify
  3. Use reviewer skill → Document and prepare handoff
  4. Update docs/session_relay.md → Pass context to next session

Pro Tips

Tip 1: Use session_relay.md as Your Starting Point
Skip re-reading full history; session_relay.md gives you:
- Current problem with solution
- What's already done
- Exact next steps
- Expected outcomes

Time saved: 15+ minutes vs. re-reading conversation

Tip 2: Reference Stage-Specific Docs
Don't search for random info:
- In Docker stage? Read docs/docker_stage.md
- In Kubernetes stage? Read docs/kubernetes_stage.md
- In AKS stage? Read docs/aks_stage.md

Tip 3: Use Skills for Coordinated Work
Skills are designed to work together:
- analyst checks current state
- developer implements changes
- reviewer documents progress

Tip 4: Update session_relay.md Before You Stop
Even if you're in the middle:
- Note what worked
- Note what failed and why
- Note exact next step
- Note any surprises

Benefit: Next session picks up instantly

Tip 5: Keep Files Updated
If you discover:
- A new pattern or fix
- A mistake in docs
- A faster way to do something
- A new blocker

Update the relevant file (takes 2-3 minutes)
Next session benefits immediately

FAQ: Which File Should I Read?

Q: I just started working on Train Ticket  
A: Read docs/deployment.md first, then docs/session_relay.md

Q: I worked on this before and forgot where we were  
A: Start with docs/session_relay.md - it's designed for this!

Q: What's the current deployment stage?  
A: Check docs/session_relay.md or docs/deployment.md

Q: How do I deploy to Docker/K8s/AKS?  
A: Read the stage-specific file (docker_stage.md, kubernetes_stage.md, aks_stage.md)

Q: What should I do right now?  
A: Check docs/session_relay.md section "Next Immediate Steps"

Q: I made progress, how do I help the next session?  
A: Update docs/session_relay.md with what you did and what's next

Q: I want to use a skill for my task  
A: Check .github/skills/README.md for quick reference

Deployment Phases at a Glance

| Phase | Status | Details | File |
|-------|--------|---------|------|
| Docker | IN PROGRESS | Local Docker Compose | docs/docker_stage.md |
| Kubernetes | TODO | Local K8s cluster | docs/kubernetes_stage.md |
| AKS | TODO | Azure Kubernetes | docs/aks_stage.md |

Getting Started Right Now

You are here: c:\Users\hasali\Projects\portfolio\train-ticket

1. Read this file (you're doing it!)
2. Open docs/session_relay.md
3. Follow "Next Immediate Steps" in that file
4. Use appropriate skill from .github/skills/
5. Update docs/session_relay.md when done

---

Created: 2026-08-25  
Purpose: Enable continuous development across deployment stages  
Maintenance: Update when moving between stages  
Last Updated: 2026-08-25
