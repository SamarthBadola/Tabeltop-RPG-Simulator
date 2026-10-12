# Tabletop RPG Simulator

A self-hosted, browser-based tool for Dungeon Masters: character management and encounter running, backed by a pure-Java rules engine and PostgreSQL. The optional pathfinding service is a separate C++17 process. This is not a virtual tabletop.

## Project baseline

- Java 25 (Temurin LTS)
- Spring Boot 4.1.1
- Maven Wrapper targeting Maven 3.10.0
- PostgreSQL 16.15 with Flyway migrations
- Thymeleaf + self-hosted HTMX
- C++17 pathfinder service (optional at runtime; Java fallback remains available)
- Docker Compose for local/LAN use
- GitHub Actions for CI; GHCR planned for versioned container images
- D&D 5e SRD 5.1 (2014 rules) only, with the required CC-BY-4.0 attribution

The toolchain entries are selected baselines. See the current implementation status below before assuming a component is already available.

## Maven and Java identifiers

- Maven \`groupId\`: \`com.github.sbadolankaur\`
- Maven \`artifactId\`: \`tabletop-rpg-simulator\`
- Java root package: \`com.github.sbadolankaur.rpg\`

## Licensing and third-party attribution

Project-owned code and documentation are licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE). Third-party components and SRD material retain their own license terms.

### SRD 5.1 attribution

This work includes material taken from the System Reference Document 5.1 (“SRD 5.1”) by Wizards of the Coast LLC and available at https://dnd.wizards.com/resources/systems-reference-document. The SRD 5.1 is licensed under the Creative Commons Attribution 4.0 International License available at https://creativecommons.org/licenses/by/4.0/legalcode.

The attribution is also recorded in [NOTICE](NOTICE). Do not add non-SRD official D&D text or artwork.

## Current status

The walking-skeleton implementation was merged in PR #6, followed by CI/governance maintenance in PRs #9, #10 and #12. CI runs repository hygiene, formatting/unit tests, PostgreSQL integration tests, secret scanning and source-built Compose smoke on pull requests and branch pushes. All five jobs passed on [PR #12 CI](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38260399502) and [the post-merge `main` run](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38260733720).

**Step 1 — walking skeleton is accepted.** On 2026-10-12, the project owner reported a successful laptop startup using the source-build Compose command: the app image built, PostgreSQL reported healthy, the application started, and first-account registration and sign-in succeeded.

The first complete image build took approximately **198 seconds** on that laptop. This is the initial cold-build baseline, not yet a performance diagnosis. Build-time optimization is a tracked follow-up: measure cold and warm builds and identify the dominant costs before changing Docker layers, Maven dependency caching, image size or build-cache configuration.

The next implementation milestone is Step 2, the dice engine. Read [the AI handoff](docs/AI-HANDOFF.md), [the progress tracker](docs/PROGRESS.md), [the decision log](docs/DECISION-LOG.md), [repository governance](docs/REPOSITORY-GOVERNANCE.md), [the build brief](docs/Tabletop%20RPG%20Simulator%20AI%20Build%20Brief.md), and [architecture v4](docs/architecture.md) before continuing.

## Local startup

Use Bash (Git Bash or WSL on Windows). From the repository root:

\`\`\`bash
./deploy/scripts/init-env.sh
docker compose -f deploy/docker-compose.yml -f deploy/docker-compose.build.yml --env-file deploy/.env up --build -d
\`\`\`

Then open http://localhost:8080 and create the first account. It becomes the instance administrator. Registration closes once that account is created; additional accounts will be enabled after the invitation flow is implemented.

To inspect logs:

\`\`\`bash
docker compose -f deploy/docker-compose.yml -f deploy/docker-compose.build.yml --env-file deploy/.env logs -f app db
\`\`\`

To stop the services without deleting database data:

\`\`\`bash
docker compose -f deploy/docker-compose.yml -f deploy/docker-compose.build.yml --env-file deploy/.env down
\`\`\`

Use \`down -v\` only when you intend to delete the database volume. Do not commit \`deploy/.env\` or disclose the generated database password.

## Engineering rules

- Implement D&D mechanics only from the project ruleset specification; do not implement mechanics from memory.
- Keep \`engine\` JDK-only and free of I/O, Spring, JPA and HTTP dependencies.
- Do not commit secrets, real \`.env\` files, local build output, or user data.
- Use additive Flyway migrations; never edit an applied migration.
- Test behavior, including negative cases. Never weaken or skip tests just to make them pass.
- Prefer efficient, simple implementations and self-documenting names. Do not add comments that merely repeat code; document public contracts or non-obvious constraints where that context is useful.
- Use short-lived branches, Conventional Commits, pull requests, and squash merges.
