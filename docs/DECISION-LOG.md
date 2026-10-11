# Decision Log

Record non-obvious choices here. A decision should state its status, context, choice, and consequences. Update this log in the same PR as the implementation that establishes the decision.

| ID | Topic | Status | Decision / note |
|---|---|---|---|
| ADR-001 | Repository topology | Accepted | One monorepo: `app/`, `pathfinder/`, `deploy/`, `docs/`, `.github/`. |
| ADR-002 | Application runtime and build tool | Accepted | Use Temurin Java 25 LTS; local version reported as `25.0.4.1+1`. Pin Spring Boot `4.1.1` and configure the checked-in Maven Wrapper to use Apache Maven `3.10.0`. Spring Boot 4.1.1 officially supports Java 17–26. Maven 3.10.0 is stable but has stricter POM validation than the 3.9 line, so fix invalid/duplicate plugin declarations rather than downgrading the build. |
| ADR-003 | Persistence | Accepted | PostgreSQL `16.15` image for the initial baseline (the architecture remains on major version 16) with Flyway; never use H2. Upgrade to the latest patched PostgreSQL 16 image through a reviewed dependency update. |
| ADR-004 | CI provider and registry | Accepted | GitHub Actions and GitHub Container Registry (GHCR). |
| ADR-005 | Git workflow | Accepted | Short-lived branches, Conventional Commits, PR checks, squash merge, linear `main`. |
| ADR-006 | Action pinning | In progress | Prefer verified, full-length commit SHA pins for third-party Actions; add a readable release-version comment and use Dependabot for updates. |
| ADR-007 | Software license | Open | Select a license for the project's own code before adding a root `LICENSE`. This is separate from SRD content licensing. |
| ADR-008 | SRD attribution | Open | Copy the required SRD 5.1 attribution notice verbatim from the authoritative SRD source into `NOTICE`; do not paraphrase or invent it. |
| ADR-009 | Image publish/release flow | Open — document discrepancy | The build brief describes building/pushing version-tagged images on `v*`; architecture v4 says publish commit-SHA images on `main`, then promote/re-tag the same image for a version release. Prefer build-once/promote-same-digest, but confirm before enabling CD. |
| ADR-010 | Java group ID / root package | Open | Replace `com.yourteam.rpg` placeholder with the team's real group ID and root package before generating the Maven project. |
| ADR-011 | GitHub owner and repository name | Open | Repository currently exists as `SamarthBadola/Tabeltop-RPG-Simulator`; decide whether to correct the apparent spelling typo and whether the public visibility is intentional before publishing project content. |
| ADR-012 | Developer container tooling | Accepted baseline | Local Docker CLI version reported as `29.7.2` (build `a7dcaa6`). Verify daemon/server version, Compose plugin, and Buildx with the commands in the setup checklist; don't install a separate Compose version if Docker Desktop/package manager already supplies the plugin. |

## Verified toolchain references (2026-10-11)

- Java: Temurin 25 LTS, local runtime version supplied by the project owner: `25.0.4.1+1`.
- Spring Boot: `4.1.1`, currently listed as the latest stable release; its official system requirements support Java through version 26.
- Maven: `3.10.0` for the Maven Wrapper distribution; current stable release with stricter POM validation.
- PostgreSQL: `16.15`, the latest PostgreSQL 16 minor release verified for this baseline.
- CMake: `4.4.4`, current stable version to use when local C++ pathfinder development begins. CMake/compiler are not needed for the Java-only walking-skeleton steps when using Docker for service builds.
- Docker Compose and Buildx: use the plugin versions installed with the Docker distribution, then record the output of `docker compose version` and `docker buildx version`. No separate manual installation should be made until this is checked.

## Dependency/version policy

Keep dependency versions exact in `pom.xml`, the Maven Wrapper configuration, Docker base image tags, and other build manifests. Confirm releases against official documentation before pinning them. Use reviewed Dependabot updates rather than floating project dependencies to `latest`.
