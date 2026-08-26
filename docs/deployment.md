---
name: train-ticket-deployment
description: Deployment of Train Ticket across Docker, Kubernetes, and AKS
---

# Deployment Guide - Train Ticket Application

## Project Overview

Project Name: Train Ticket Microservice System  
Current Focus: Multi-stage deployment (Docker > K8s > AKS)  
Status: Local Docker deployment (IN PROGRESS)  
Goal: Deploy 41 microservices across Docker, local Kubernetes, and Azure AKS

## Deployment Stages

### Stage 1: Local Docker Deployment
- Deploy all 41 services in Docker Compose
- Configure local MongoDB, MySQL, Redis
- Verify services communicate correctly
- Access web UI at http://localhost:8080
- Current Status: IN PROGRESS

### Stage 2: Local Kubernetes Deployment
- Package services as Kubernetes manifests
- Deploy to local K8s cluster (Minikube or Docker Desktop K8s)
- Test persistent volumes and networking
- Verify service discovery and communication
- Status: PENDING (after Docker stage complete)

### Stage 3: Azure Kubernetes Service (AKS) Deployment
- Configure AKS cluster in Azure
- Deploy images to Azure Container Registry
- Configure networking and ingress
- Set up monitoring and logging
- Configure auto-scaling policies
- Status: PENDING (after local K8s complete)

## Architecture

41 Java/Spring Boot microservices:
- Authentication and authorization services
- User and order management
- Route planning and ticket services
- Payment and refund services
- Food delivery integration
- And more...

Infrastructure:
- MongoDB: Document database for services
- MySQL: Relational database (voucher service)
- Redis: Caching layer
- Docker Compose: Local orchestration
- Kubernetes: Container orchestration (local and AKS)

## Key Configuration Files

### Docker Configuration
- docker-compose.yml - Local service orchestration
- */Dockerfile - 41 service container definitions
- .dockerignore - Build optimization

### Kubernetes Configuration
- deployment/kubernetes-manifests/ - K8s resources
- charts/ - Helm chart definitions (optional)
- kustomize/ - Kustomization files (optional)

### Infrastructure
- pom.xml - Maven parent configuration
- */pom.xml - Service dependencies
- hack/build-image.sh - Docker image builder
- hack/deploy/ - Deployment scripts

## Current Status

### Completed
- Java 8 to Java 11 upgrade
- All 41 services compile successfully
- Docker images configured for all services
- Docker infrastructure versions pinned

### In Progress
- Fix Docker base image (openjdk:11-jre-slim to eclipse-temurin:11-jre-alpine)
- Fix bash script line endings for WSL
- Build and test Docker images locally
- Deploy with docker-compose

### Pending
- Push images to container registry
- Deploy to local Kubernetes
- Configure Kubernetes networking
- Deploy to AKS
- Set up monitoring and auto-scaling

## Documentation Structure

- docs/deployment.md - This file (overview)
- docs/docker_stage.md - Docker deployment details
- docs/kubernetes_stage.md - Local K8s deployment details
- docs/aks_stage.md - Azure AKS deployment details
- docs/session_relay.md - Current session handoff
- .github/skills/ - Three specialized skills for deployment

## Quick Reference

### Skills Available

Analyst Skill (.github/skills/analyst.md)
- Verify deployment readiness
- Test container images
- Validate configurations
- Identify issues

Developer Skill (.github/skills/developer.md)
- Implement deployment configurations
- Build and package services
- Execute deployment tasks
- Fix infrastructure issues

Reviewer Skill (.github/skills/reviewer.md)
- Document deployment progress
- Verify stage completion
- Prepare handoff to next phase
- Update tracking

### Common Commands

Maven:
  mvn clean install -DskipTests - Build all services

Docker:
  docker-compose up -d - Start local deployment
  docker-compose ps - Check service status
  docker-compose logs -f - View logs

Kubernetes:
  kubectl apply -f deployment/kubernetes-manifests/ - Deploy to K8s
  kubectl get pods - Check pod status
  kubectl logs [pod-name] - View pod logs

Azure:
  az aks create - Create AKS cluster
  az acr build - Build image in registry
  kubectl apply --kubeconfig=[path] - Deploy to AKS

## Success Criteria

### Docker Stage
- All 41 services running in containers
- Services communicate through Docker network
- Web UI accessible at http://localhost:8080
- Database services (MongoDB, MySQL, Redis) healthy
- No error logs in container output

### Local Kubernetes Stage
- K8s cluster created and running
- All 41 service pods running
- Services discover each other via DNS
- Persistent volumes working
- Ingress configured and accessible

### AKS Stage
- AKS cluster provisioned
- Images pushed to Azure Container Registry
- Services deployed and running
- Networking and ingress configured
- Auto-scaling policies active
- Monitoring and logging enabled

## Next Steps

1. Complete Docker stage deployment
2. Test all services in Docker Compose
3. Push images to registry
4. Set up local Kubernetes cluster
5. Deploy Kubernetes manifests locally
6. Test Kubernetes deployment
7. Provision AKS cluster
8. Deploy to AKS
9. Configure monitoring and auto-scaling

---

Last Updated: 2026-08-25  
Focus: Multi-stage deployment across Docker, Kubernetes, and AKS  
Next Milestone: Docker stage completion and local testing
