# AI and Contributor Handoff

Last reconciled: 2026-10-12. This is the durable source of continuity for contributors and AI assistants. Re-check files, CI results, and live GitHub settings before relying on time-sensitive details.

## Read these sources first

1. \`docs/AI-HANDOFF.md\` — this continuity snapshot and next task.
2. \`docs/PROGRESS.md\` — milestone and acceptance checklist.
3. \`docs/DECISION-LOG.md\` — accepted decisions and unresolved decisions.
4. \`docs/REPOSITORY-GOVERNANCE.md\` — rulesets and verification gaps.
5. \`docs/Tabletop RPG Simulator AI Build Brief.md\` — constraints, testing rules and build order.
6. \`docs/architecture.md\` — architecture v4.
7. \`README.md\`, \`CONTRIBUTING.md\`, \`SECURITY.md\`, \`NOTICE\`, \`LICENSE\` and \`.github/workflows/ci.yml\` as relevant.

A more specific approved spec takes precedence over a high-level brief. When documents conflict or leave an implementation-critical choice open, describe the discrepancy and ask the human. Never silently invent D&D rules. Keep this handoff, the progress tracker and the decision log aligned.

## Project identity and fixed baseline

- Repository: public \`SamarthBadola/Tabletop-RPG-Simulator\`.
- Product: self-hosted browser-based D&D 5e SRD 5.1 (2014) character manager and encounter runner; not a virtual tabletop.
- Two-person course project; owner is the maintainer. Do not guess the teammate's GitHub handle.
- Maven \`groupId\`: \`com.github.sbadolankaur\`; \`artifactId\`: \`tabletop-rpg-simulator\`; Java root package \`com.github.sbadolankaur.rpg\`.
- Selected baseline: Temurin Java 25, Spring Boot 4.1.1, Maven Wrapper 3.10.0, PostgreSQL 16.15.
- Owner-reported local versions: Git \`2.54.0.windows.1\`, Java \`25.0.4.1+1\`, Docker Engine/CLI \`29.8.2\`, Compose \`5.5.1\`, Buildx \`0.37.2\`; Docker Desktop Linux engine with context \`desktop-linux\`.
- Project-owned code/docs use Apache-2.0. SRD 5.1 material has separate CC-BY-4.0 terms, with attribution in \`README.md\` and \`NOTICE\`.
- GitHub Actions and GHCR are intended CI/CD and registry tools. Release publishing is not implemented; ADR-009 is unresolved.

## Current implementation state

The walking skeleton was merged in PR #6. CI/governance improvements were merged in PR #9, and the Dependabot Compose-ecosystem correction was merged in PR #10. Local first-start acceptance passed on 2026-10-12, as reported by the project owner. Step 1 was accepted on 2026-10-12 after the project owner reported a successful laptop source-build startup, healthy PostgreSQL, successful application startup, first-account registration and sign-in. The first image build took approximately 197.8 seconds. Track build-performance optimization as a follow-up and measure the actual bottlenecks before changing the build.

The merged walking skeleton provides:

- Maven project and wrapper targeting Java 25/Spring Boot 4.1.1/Maven 3.10.0.
- Spring Boot service with Actuator health/readiness.
- PostgreSQL configuration, Flyway initial schema and Hibernate schema validation.
- Username/password registration/login, BCrypt, CSRF, HttpOnly/SameSite cookies, configurable Secure cookie flag, restrictive CSP and bounded login throttling.
- Pessimistically locked first-account initialization state: the first successful registration becomes the instance administrator; later public registration is closed pending invitations.
- Thymeleaf login, registration and authenticated landing pages.
- App Dockerfile, PostgreSQL-only Compose deployment plus a source-build overlay, environment initializer and smoke test.
- Unit tests and PostgreSQL Testcontainers integration tests.
- CI jobs for repository hygiene, unit/format checks, PostgreSQL integration, secrets scanning, and source-built Compose smoke on pull requests and branch pushes.

CI evidence: [PR #12 run](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38260399502) and [post-merge `main` run](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38260733720) passed all five GitHub Actions jobs. The live `main-quality-gates` ruleset additionally requires `DCO`, so six contexts are required for merging.

## First-run instructions

Use Bash (Git Bash or WSL on Windows) at the repository root:

\`\`\`bash
./deploy/scripts/init-env.sh
docker compose -f deploy/docker-compose.yml -f deploy/docker-compose.build.yml --env-file deploy/.env up --build -d
\`\`\`

Open \`http://localhost:8080\` and create the first instance-admin account. Later open registration is closed until Step 4 introduces invites. Stop without deleting data using \`docker compose ... down\`; use \`down -v\` only when you intend to delete the database volume. Never commit \`deploy/.env\`.

## Non-negotiable engineering constraints

- Do not implement D&D mechanics from memory. The ruleset spec is not written.
- Until specs exist, follow Steps 1–5 in the build brief.
- Keep \`engine\` pure JDK: no Spring, JPA, HTTP, or filesystem I/O.
- Enforce feature package boundaries and ArchUnit rules specified in architecture v4.
- Keep I/O outside database write transactions; migrations are additive and applied migrations are immutable.
- Security by default: deny-by-default routes except named public endpoints, data-layer authorization, form DTOs not JPA entities, CSRF, validation, no secrets in files/logs, uploads/imports untrusted.
- Test behavior and negative cases; never weaken tests to get green.
- Prefer minimal dependencies and simple designs. Keep code efficient; optimize only where the work is justified by the actual algorithm and use case.
- Comment only where it adds information: public contracts, non-obvious constraints/reasons/invariants. Avoid redundant comments or a comment quota.
- Pin tool/dependency/image versions; verify APIs/release details against primary docs.
- Use short-lived branches, Conventional Commits, DCO sign-off, PRs and squash merges. Do not commit directly to \`main\`.
- Update progress/decision docs alongside implementation; file existence alone does not prove done-when criteria.

## Next implementation sequence

1. Begin Step 2 (dice engine) in a separate short-lived PR. Follow the grammar, limits and tests in the build brief; do not invent D&D rules.
2. Keep first-build performance on the implementation checklist. Baseline: approximately 197.8 seconds for the first laptop Docker image build on 2026-10-12. Benchmark cold vs. warm builds, separate downloads from compilation/package and startup, then optimize cache layers and image/build performance based on evidence. Avoid speculative optimizations and preserve a reproducible clean-clone build.
3. Stop after Step 5 and request the ruleset, content-schema, grid/geometry, and UX/API-with-threat-model specifications before implementing later gameplay features.

## Unresolved choices

- ADR-009: build brief says publish images on version tags; architecture v4 says publish commit-SHA images on main, then promote the same digest to release tags. Preferred direction is build once/promote same digest, but ask the human before enabling CD.
- Future content decisions: spell/monster subsets, surprise handling, DM override display, corner-cutting, upload limits and journal event capture.
