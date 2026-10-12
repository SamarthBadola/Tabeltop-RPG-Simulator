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

Repository bootstrap and documentation reconciliation are merged. The walking-skeleton implementation lives on \`feat/walking-skeleton\` and is under review until PR CI and runtime acceptance are verified.

The branch adds:
- Maven project and wrapper targeting Java 25/Spring Boot 4.1.1/Maven 3.10.0.
- Spring Boot service with Actuator health/readiness.
- PostgreSQL configuration, Flyway initial schema and Hibernate schema validation.
- Username/password registration/login, BCrypt, CSRF, HttpOnly/SameSite cookies, configurable Secure cookie flag, restrictive CSP and bounded login throttling.
- Pessimistically locked first-account initialization state: the first successful registration becomes the instance administrator; later public registration is closed pending invitations.
- Thymeleaf login, registration and authenticated landing pages.
- App Dockerfile, PostgreSQL-only Compose deployment plus a source-build overlay, environment initializer and smoke test.
- Unit tests and PostgreSQL Testcontainers integration tests.
- CI jobs for repository hygiene, unit/format checks, PostgreSQL integration, secrets scan and main-only Compose smoke.

**Do not claim the implementation is verified until checking the current PR's jobs.** The authoring environment used for the assistant lacks Java 25, Maven, Docker, and outbound network access, so a local application build has not been executed by the assistant. GitHub Actions is expected to validate the branch; inspect results and fix failures honestly.

## First-run instructions in the walking-skeleton branch

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

1. Finish reviewing/validating the walking skeleton on \`feat/walking-skeleton\`.
2. Wait for all applicable CI jobs to pass. The Compose smoke job is currently main-only. Add \`unit\` and \`integration\` to the live \`main-quality-gates\` required checks in GitHub Settings after those contexts appear; repository rulesets are not editable from the connected GitHub API.
3. Merge the implementation PR only after the human has reviewed it and checks pass. Run a fresh-clone first-start test with Docker Desktop if needed.
4. Update README, progress and handoff with actual test results.
5. Only then begin Step 2 dice engine as a separate PR.

Stop after Step 5 and request the ruleset, content-schema, grid/geometry, and UX/API-with-threat-model specs before later gameplay features.

## Unresolved choices

- ADR-009: build brief says publish images on version tags; architecture v4 says publish commit-SHA images on main, then promote the same digest to release tags. Preferred direction is build once/promote same digest, but ask the human before enabling CD.
- Future content decisions: spell/monster subsets, surprise handling, DM override display, corner-cutting, upload limits and journal event capture.
