# TrainTicket Platform — Hand-Built `infrastructure/` Roadmap (Repo 1)

## Context

This is the **platform layer** of the Day-2 RCA project, built as a deliberate
learning exercise: stand up and instrument FudanSELab TrainTicket (47 services —
42 Java, 1 Node, 2 Python) on Kubernetes, wiring everything by hand with
Kustomize + Argo CD + ingress/egress + a service mesh + full observability
(Prometheus/Thanos, Tempo, Loki) + SLI alerting → Slack. This platform becomes the
substrate the later RCA agent harness (Repo 2) consumes; Repo 2 is **not** in scope here.

Ground decisions:
- **Keep the upstream repo as reference.** Nothing is deleted. FudanSELab's
  `deployment/` is read-only source material for ports, env vars, and dependency wiring.
- **All platform work lives in this `infrastructure/` directory.**
- **Image strategy (use upstream `codewisdom/ts-*` images vs build your own) is deferred**
  to Layer 1 — the roadmap isolates image refs in an overlay so the switch is one line.
- **Roles:** you write the YAML; the roadmap sequences the build and defines the gates.
  Review happens at Checkpoints A–E before any pattern is scaled across all 47 services.

## Ground rules for the build

1. **Reference, don't copy.** Use upstream manifests to learn *what* each service needs
   (image, ports, env, DB, dependency), then express it *your* way in Kustomize. Copy-pasting
   their YAML defeats the learning goal.
2. **One layer at a time, each with an acceptance gate.** Don't start a layer until the
   previous gate is green. The gates are the spine of the project.
3. **GitOps from Layer 2 onward.** Once Argo CD is in, the cluster is changed *only* by git
   commits to `infrastructure/`, never by `kubectl apply`. This is the discipline to build.
4. **Review checkpoint = open a PR / push the branch.** Review structure, idioms, and
   correctness before scaling a pattern across all 47 services.

## Recommended `infrastructure/` layout (adjust as you learn)

```
infrastructure/
├── clusters/                 # per-cluster entrypoints (local-kind/, eks-dev/)
├── bootstrap/                # Argo CD install + root app-of-apps
├── apps/
│   └── train-ticket/
│       ├── base/             # one kustomization per service + infra deps (Nacos/RabbitMQ/MySQL)
│       └── overlays/         # dev / prod patches (replicas, resources, image tags)
├── platform/
│   ├── ingress/              # ingress controller / Gateway API + routes + TLS
│   ├── mesh/                 # Istio (or Linkerd): injection, mTLS, egress policy
│   ├── observability/
│   │   ├── kube-prometheus-stack/
│   │   ├── thanos/
│   │   ├── tempo/
│   │   ├── loki/
│   │   └── grafana/          # datasources + dashboards + trace-to-logs correlation
│   └── alerting/             # SLI alert rules + Slack contact point
└── chaos/                    # fault injection (Chaos Mesh and/or Istio)
```

## The layered roadmap

Each layer: **Build → Learn → Reference → Acceptance gate → Review checkpoint.**

### Layer 0 — Cluster foundation
- **Build:** A reproducible cluster. **Recommended: start on local `kind`/`k3d`** to learn the
  layers cheaply; promote to EKS (Terraform) only once the stack is stable. Keeps the cost risk
  out of the early, high-iteration phase.
- **Learn:** cluster bootstrap, kubeconfig/contexts, namespaces, `kubectl` plumbing.
- **Reference:** none needed.
- **Gate:** `kubectl get nodes` healthy; a throwaway nginx pod schedules and is reachable.
- **Review:** cluster bring-up scripts / Terraform skeleton (if EKS).

### Layer 1 — App on Kustomize (the heavy lift)
- **Build:** Deploy TrainTicket via your own Kustomize. Sequence to avoid drowning:
  (a) infra deps first — **Nacos, RabbitMQ, per-service MySQL**; (b) a **vertical slice** of
  ~5–8 services that satisfies one user flow (login → query → book → pay); (c) the remaining services.
- **Learn:** Kustomize bases, overlays, components, strategic-merge & JSON patches, `kustomization.yaml`,
  ConfigMap/Secret generators. This is where the **image decision** surfaces (set image refs in an
  overlay so upstream-vs-self-built is a one-line change later).
- **Reference:** `deployment/kubernetes-manifests/quickstart-k8s/` (service set, ports, env),
  `deployment/kubernetes-manifests/quickstart-k8s/charts/` (Nacos/RabbitMQ/MySQL deps),
  `docker-compose.yml` (dependency graph + env), upstream `ts-deployment-part{1,2,3}.yml`.
- **Gate:** full system reaches Ready; you can **book a ticket end-to-end** through the UI/API.
- **Review:** **Checkpoint A** — review the base+overlay for the *first* service before you
  replicate the pattern 47×. Then a second review of the dependency/init-order handling.

### Layer 2 — GitOps with Argo CD
- **Build:** Install Argo CD into the cluster; a root **app-of-apps** pointing at `infrastructure/`.
  Move Layer-1 app + Layer-0 add-ons under Argo management.
- **Learn:** `Application`/`ApplicationSet`, sync waves (ordering Nacos→DBs→services), sync policies,
  **automated self-heal + prune**, drift detection.
- **Reference:** none in-repo (no Argo today) — this is net-new.
- **Gate:** a git commit to `infrastructure/` auto-syncs; manually delete a Deployment and Argo
  **heals it back**; intentional drift is reported.
- **Review:** **Checkpoint B** — app-of-apps structure + sync-wave ordering.

### Layer 3 — Ingress / egress
- **Build:** Ingress controller (or Gateway API) exposing the frontend; lock down **egress**
  with NetworkPolicies (and/or mesh egress at Layer 4).
- **Learn:** Ingress vs Gateway API, TLS termination, host/path routing, default-deny NetworkPolicy.
- **Reference:** upstream Istio `VirtualService`/`Gateway` in `deployment/kubernetes-manifests/k8s-with-istio/`.
- **Gate:** external request reaches the frontend through your ingress over TLS; egress to
  non-allowed destinations is blocked.
- **Review:** ingress + NetworkPolicy review.

### Layer 4 — Service mesh + sidecars
- **Build:** Install a mesh (**Istio recommended** — upstream already has an overlay to learn from,
  and it doubles as your zero-code tracing + RED source). Enable sidecar injection + mTLS.
- **Learn:** sidecar injection, mTLS, mesh traffic policy, and crucially **mesh-emitted telemetry**
  (OTLP traces + RED metrics) — this is what lets you instrument 42 Java + 3 polyglot services
  **without touching app code**. Note the one real gap: verify trace-context (`traceparent`/B3)
  propagates across the **Node + Python** services so traces don't break mid-chain.
- **Reference:** `deployment/kubernetes-manifests/k8s-with-istio/`.
- **Gate:** sidecars injected across the namespace; mTLS on; traffic flows; mesh is emitting telemetry.
- **Review:** **Checkpoint C** — mesh config + verification that polyglot trace context survives a full hop chain.

### Layer 5 — Metrics: Prometheus + Thanos
- **Build:** kube-prometheus-stack; ServiceMonitors over services + mesh; **Thanos** for HA /
  long-term / global query.
- **Learn:** ServiceMonitor/PodMonitor, recording rules, RED metrics, Thanos sidecar/store/query/compactor.
- **Reference:** `deployment/kubernetes-manifests/prometheus/` (their Prometheus+Grafana+AlertManager).
- **Gate:** per-service RED (rate/errors/duration) visible in Grafana; Thanos serves a historical query
  beyond local Prometheus retention.
- **Review:** ServiceMonitor coverage + Thanos topology review.

### Layer 6 — Traces (Tempo) + logs (Loki)
- **Build:** Tempo receiving OTLP spans from the mesh; Loki for logs labelled by service/pod with
  **trace IDs**; Grafana datasources for Prometheus + Tempo + Loki with **trace-to-logs correlation**.
- **Learn:** Tempo ingest/query, Loki labels & cardinality, Grafana correlation (span → logs → metrics).
- **Reference:** Jaeger overlay `deployment/kubernetes-manifests/k8s-with-jaeger/` (trace concepts only),
  EFK `deployment/kubernetes-manifests/efk-deployment/` (log-shipping concepts only).
- **Gate (the RCA substrate):** a single request is **traceable end-to-end across multiple hops** in
  Tempo; clicking a span jumps to that service's logs.
- **Review:** **Checkpoint D** — trace quality (span names, DB spans, error tags) good enough that *you*
  can localize a fault by hand. If you can't, the agent never will — fix instrumentation here.

### Layer 7 — SLI alerting → Slack
- **Build:** Prometheus/Grafana alert rules on **user-facing SLIs** (success-rate + latency per service);
  Slack contact point → `#platform-alerts`.
- **Learn:** SLI/SLO thinking, alert rules, the "only alert on user-facing degradation" SRE filter.
- **Reference:** AlertManager config in `deployment/kubernetes-manifests/prometheus/`.
- **Gate:** induce a latency/error-rate degradation → SLI alert fires → Slack message posts.
- **Review:** alert-rule thresholds + routing review.

### Layer 8 — Fault injection (RCA handoff gate)
- **Build:** A fault harness — **Chaos Mesh** (pod kill / network delay / resource stress) and/or
  Istio fault injection — to reproduce representative faults from the two buckets (config-fixable
  e.g. memory-limit/OOM; code/architecture e.g. latency/contention).
- **Learn:** chaos experiments, reproducible fault scripting, mapping symptom → faulting service.
- **Reference:** `deployment/fault-inject-deployment/` (Istio fault manifests + `gray-release-manage.py`).
- **Gate — HANDOFF CONTRACT:** inject one config fault and one code fault; for each, the **SLI alert
  fires, Slack posts, and you localize the faulting service by hand from the Tempo trace**. This is the
  green light to start Repo 2 (the agent).
- **Review:** **Checkpoint E** — fault catalogue mapping + handoff verification.

## Cross-cutting decisions deferred (revisit at the noted layer)
- **Upstream images vs build-your-own** → decide at **Layer 1** (isolate image refs in an overlay so it's a one-line switch).
- **Local `kind` vs EKS** → recommend local first (Layer 0), promote to EKS after Layer 6; add teardown + budget alarm with EKS.
- **Istio vs Linkerd** → recommend Istio (Layer 4) for the upstream reference + richer telemetry.
- **SkyWalking (already in the Java POMs) vs Tempo** → roadmap targets Tempo via mesh OTLP (no app code); SkyWalking can stay off.

## Explicitly out of scope (separate effort)
- The **RCA agent harness (Repo 2)** — `.claude/agents`, skills, Slack listener, eval/ground-truth.
  Starts only after the Layer 8 handoff gate is green.
- Authoring the **22-fault ground-truth labels** (lives with Repo 2).

## Verification (how we know each step worked)
The **acceptance gate** under each layer is the test — concrete and demoable, in order:
nodes healthy → book a ticket → Argo self-heals → ingress+egress enforced → mesh telemetry flowing →
RED+Thanos → **end-to-end trace across hops** → SLI alert hits Slack → **hand-localize an injected fault**.
The Layer 6 and Layer 8 gates are the load-bearing ones for the RCA story. YAML is reviewed at
Checkpoints A–E before any pattern is scaled.
