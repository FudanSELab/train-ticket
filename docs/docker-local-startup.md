---
name: docker-local-startup
description: Guide for starting Train Ticket services locally with Docker
---

# Docker Local Startup Guide

This guide walks you through building and running all 41 Train Ticket microservices locally using Docker Compose.

## Prerequisites

Before starting, ensure you have:
- **Docker Desktop** installed and running
- **Docker Compose** (included with Docker Desktop)
- **Maven 3.8+** (for building Java services)
- **Java 11+** (for Maven compilation)
- At least **8GB RAM** available (recommended: 16GB for comfortable local development)
- **20GB free disk space** for Docker images and containers

Verify prerequisites:
```bash
docker --version
docker-compose --version
mvn --version
java --version
```

## Quick Start (3 Steps)

### Step 1: Build All Java Services

Build all 41 microservices with Maven:
```bash
bash .github/scripts/developer/maven-build.sh full-build
```

This compiles all services and generates `.jar` files in each service's `target/` directory.

**Expected output**: All services compile successfully with `BUILD SUCCESS`

### Step 2: Build Docker Images

Build Docker images for all services:
```bash
bash .github/scripts/developer/docker-build.sh build-all
```

This creates Docker images using the updated `eclipse-temurin:11-jre-alpine` base image.

**Expected output**: All 41 images built successfully (check with `docker images | grep ts-`)

### Step 3: Start Services with Docker Compose

Start all services and infrastructure:
```bash
bash .github/scripts/developer/docker-compose.sh up
```

This starts:
- 41 Train Ticket microservices
- 24 MongoDB instances (one per service needing data)
- 1 MySQL instance (shared database)
- 7 Redis instances (distributed caching)

**Expected output**: Containers starting, logs streaming. Look for port binding messages.

## Detailed Steps

### Build Maven Projects

If you prefer manual control over the build:

```bash
# Full clean build
mvn clean install -DskipTests

# Build single service
cd ts-auth-service
mvn clean package -DskipTests
cd ..
```

**Troubleshooting**:
- If build fails, check `.github/scripts/developer/maven-build.sh` for common issues
- Verify Java 11: `java -version` should show version 11.x.x
- Check Maven: `mvn --version` should show 3.8 or higher

### Build Docker Images

```bash
# Build all 41 images
bash .github/scripts/developer/docker-build.sh build-all

# Build single service
bash .github/scripts/developer/docker-build.sh build-single ts-auth-service

# Force rebuild without cache
bash .github/scripts/developer/docker-build.sh build-no-cache
```

**Troubleshooting**:
- If Dockerfile errors occur, verify base image is `eclipse-temurin:11-jre-alpine`
- Check `.github/scripts/developer/bulk-updates.sh verify-base-image` to confirm all Dockerfiles updated
- Ensure `.jar` files exist in each service's `target/` directory before building images

### Start Services

#### Option A: Full Stack (Recommended for first time)

```bash
# Start all services in foreground (see logs)
bash .github/scripts/developer/docker-compose.sh up

# Or start in background (detached mode)
bash .github/scripts/developer/docker-compose.sh up -d
```

#### Option B: Individual Control

```bash
# Start specific service
docker-compose up -d ts-auth-service

# Restart service
docker-compose restart ts-auth-service

# View logs
bash .github/scripts/developer/docker-compose.sh logs ts-auth-service

# Follow logs (tail)
bash .github/scripts/developer/docker-compose.sh logs-follow ts-auth-service
```

## Verify Deployment

### Check Service Status

```bash
# List running containers
bash .github/scripts/developer/docker-compose.sh status

# See container health
docker ps
```

### Check Service Health

```bash
# Run health checks
bash .github/scripts/analyst/docker-checks.sh status

# Check specific service logs
bash .github/scripts/analyst/docker-checks.sh logs ts-auth-service

# Inspect service network
bash .github/scripts/analyst/docker-checks.sh network
```

### Test Service Connectivity

Once services are running, test endpoints:

```bash
# Example: Auth Service (port 12349)
curl http://localhost:12349/health

# Example: Gateway Service (port 8080)
curl http://localhost:8080/health

# List all exposed ports
docker ps --format "table {{.Names}}\t{{.Ports}}"
```

## Stop and Cleanup

### Stop Services (Keep data)

```bash
# Stop all containers
bash .github/scripts/developer/docker-compose.sh down

# Stop specific service
docker-compose stop ts-auth-service
```

### Remove Everything (Clean slate)

```bash
# Stop and remove containers, networks, volumes
bash .github/scripts/developer/docker-compose.sh down-volumes

# Remove all Train Ticket images
bash .github/scripts/developer/docker-build.sh cleanup

# Remove dangling images
bash .github/scripts/developer/docker-build.sh cleanup-dangling
```

## Common Issues

### Issue: Port Already in Use

**Error**: `Error binding to 0.0.0.0:<port>: Address already in use`

**Solution**:
```bash
# Find and kill process on port
bash .github/scripts/developer/troubleshooting.sh check-port 8080
bash .github/scripts/developer/troubleshooting.sh kill-port 8080
```

### Issue: Image Not Found

**Error**: `docker.io/library/<image>: not found`

**Solution**:
- Verify image was built: `docker images | grep ts-`
- Rebuild if missing: `bash .github/scripts/developer/docker-build.sh build-all`

### Issue: Container Exits Immediately

**Error**: Container starts then stops with exit code 1

**Solution**:
```bash
# Check logs for error
bash .github/scripts/developer/docker-compose.sh logs <service-name>

# Check service-specific requirements (MongoDB, MySQL, Redis availability)
bash .github/scripts/analyst/docker-checks.sh logs-follow <service-name>
```

### Issue: Disk Space

**Error**: `no space left on device`

**Solution**:
```bash
# Check disk usage
df -h

# Clean up Docker (prune unused images/volumes)
docker system prune -a
```

## Performance Tuning

For resource-constrained machines:

### Reduce Memory Usage
Edit `docker-compose.yml` and reduce JVM heap size:
```yaml
services:
  ts-auth-service:
    environment:
      JAVA_OPTS: "-Xmx128m -Xms64m"  # Reduce from 200m
```

### Start Specific Services Only
```bash
# Start only auth, user, and gateway services
docker-compose up -d ts-auth-service ts-user-service ts-gateway-service
```

### Use Health Checks
```bash
# Monitor only critical services
bash .github/scripts/developer/docker-compose.sh status | grep -E "ts-auth|ts-gateway|ts-user"
```

## Next Steps

Once Docker services are running locally:

1. **Test Endpoints**: Verify services respond via their exposed ports
2. **Deploy to Kubernetes**: See [docs/kubernetes-local-startup.md](kubernetes-local-startup.md)
3. **Deploy to AKS**: See [docs/aks-deployment.md](aks-deployment.md)
4. **Debug Issues**: Use scripts in `.github/scripts/analyst/` for diagnostics

## References

- [Main Deployment Overview](deployment.md)
- [Build and Push Scripts](../README.md)
- [Docker Compose File](../docker-compose.yml)
- [Service Configuration](../docker-compose.yml)
