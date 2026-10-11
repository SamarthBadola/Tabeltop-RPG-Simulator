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
| ADR-007 | Software license | Accepted | License project-owned code and documentation under Apache License 2.0 (`Apache-2.0`). It permits use, modification, and redistribution, includes an express patent license, and does not require downstream forks to publish their modifications. SRD and third-party materials retain their own licenses. |
| ADR-008 | SRD attribution | Accepted | Add the exact SRD 5.1 attribution statement specified on the official SRD 5.1 Legal Information page to `README.md`, verbatim. Do not add any other Wizards of the Coast attribution; keep SRD terms distinct from the project's Apache-2.0 license. |
| ADR-009 | Image publish/release flow | Open — document discrepancy | The build brief describes building/pushing version-tagged images on `v*`; architecture v4 says publish commit-SHA images on `main`, then promote/re-tag the same image for a version release. Prefer build-once/promote-same-digest, but confirm before enabling CD. |
| ADR-010 | Maven coordinates and Java root package | Accepted | Use Maven `groupId` `com.github.sbadolankaur`, `artifactId` `tabletop-rpg-simulator`, and Java root package `com.github.sbadolankaur.rpg`. Keep the artifact ID unchanged. |
| ADR-011 | GitHub owner, repository name and visibility | Accepted | Keep the public repository `SamarthBadola/Tabletop-RPG-Simulator`. |
| ADR-012 | Developer tooling baseline | Accepted | Local versions reported by the project owner: Git `2.54.0.windows.1`; Temurin Java `25.0.4.1+1`; Docker Engine and CLI `29.8.2` (Linux engine, `desktop-linux` context); Docker Compose `5.5.1`; Docker Buildx `0.37.2`. The Docker daemon is running. Use the supplied Docker Desktop plugins; do not install separate Compose or Buildx versions. |
| ADR-013 | Main-branch governance | Rulesets reported created; DCO check pending | Only `SamarthBadola` is authorized to merge into `main`. Use two rulesets targeting `main`: (1) `main-quality-gates`, with no bypass list, requires PRs, one approval, dismissal of stale approvals, required checks `repo-hygiene`, `secrets`, and the DCO App's exact status-check context, resolved conversations, linear history, and blocks force pushes/deletion; (2) `main-update-access`, restricts updates and allows the `Repository administrators` role to bypass for pull requests only. The owner can merge PRs but cannot push directly around the quality gates. `.github/CODEOWNERS` requests owner review; do not require code-owner approval until Nihchal's verified GitHub username is added as a second code owner. The repository owner reported both rulesets created and in enforcement; verify behavior after adding DCO as a required status check. |

| ADR-014 | DCO policy | Configuration committed; App installation and required check pending | Use the DCO GitHub App (`dcoapp/app`) to require matching `Signed-off-by` commit trailers for non-owner contributors. Keep `.github/dco.yml` with `require.members: false`; for this personal repository, the App documents that the owner is exempt from sign-off. Install the App on this repository and add its exact status-check context to the existing `main-quality-gates` ruleset. No new ruleset is required. |

## Verified toolchain references (2026-10-11)

- Java: Temurin 25 LTS, local runtime version supplied by the project owner: `25.0.4.1+1`.
- Spring Boot: `4.1.1`, currently listed as the latest stable release; its official system requirements support Java through version 26.
- Maven: `3.10.0` for the Maven Wrapper distribution; current stable release with stricter POM validation.
- PostgreSQL: `16.15`, the latest PostgreSQL 16 minor release verified for this baseline.
- CMake: `4.4.4`, current stable version to use when local C++ pathfinder development begins. CMake/compiler are not needed for the Java-only walking-skeleton steps when using Docker for service builds.
- Git: `2.54.0.windows.1`, as reported locally.
- Docker Engine and CLI: `29.8.2`; `docker info` confirms the Linux engine is running under Docker Desktop with context `desktop-linux`.
- Docker Compose: `5.5.1`, supplied by Docker Desktop.
- Docker Buildx: `0.37.2`, supplied by Docker Desktop; no separate manual installation is needed.

## Dependency/version policy

Keep dependency versions exact in `pom.xml`, the Maven Wrapper configuration, Docker base image tags, and other build manifests. Confirm releases against official documentation before pinning them. Use reviewed Dependabot updates rather than floating project dependencies to `latest`.
