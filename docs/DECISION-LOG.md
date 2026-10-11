# Decision Log

Record non-obvious choices here. A decision should state its status, context, choice, and consequences. Update this log in the same PR as the implementation that establishes the decision. Last reconciled: 2026-10-12.

| ID | Topic | Status | Decision / note |
|---|---|---|---|
| ADR-001 | Repository topology | Accepted | One monorepo: `app/`, `pathfinder/`, `deploy/`, `docs/`, and `.github/`. The application, deployment, and pathfinder folders currently contain README placeholders; the implementation has not started. |
| ADR-002 | Application runtime and build tool | Accepted | Use Temurin Java 25 LTS; local version reported as `25.0.4.1+1`. Pin Spring Boot `4.1.1` and configure the checked-in Maven Wrapper to use Apache Maven `3.10.0`. Spring Boot 4.1.1 officially supports Java 17–26. Maven 3.10.0 has stricter POM validation than the 3.9 line, so fix invalid/duplicate plugin declarations rather than downgrading the build. |
| ADR-003 | Persistence | Accepted | PostgreSQL `16.15` image for the initial baseline (the architecture remains on major version 16) with Flyway; never use H2. Upgrade to the latest patched PostgreSQL 16 image through a reviewed dependency update. |
| ADR-004 | CI provider and registry | Accepted | GitHub Actions and GitHub Container Registry (GHCR). CI currently verifies repository hygiene and scans for secrets only. Application build/test and release workflows do not exist yet. |
| ADR-005 | Git workflow | Accepted | Short-lived branches, Conventional Commits, pull requests, and linear `main`; squash merge is the project convention. The repository's active rulesets are detailed in ADR-013 and `docs/REPOSITORY-GOVERNANCE.md`. |
| ADR-006 | Action pinning | In progress | Prefer verified, full-length commit SHA pins for third-party Actions; add a readable release-version comment and use Dependabot for updates. The current bootstrap workflow pins its checkout and Gitleaks actions to full SHAs. Continue this practice when adding workflow steps. |
| ADR-007 | Software license | Accepted | License project-owned code and documentation under Apache License 2.0 (`Apache-2.0`). It permits use, modification, and redistribution, includes an express patent license, and does not require downstream forks to publish their modifications. SRD and third-party materials retain their own licenses. |
| ADR-008 | SRD attribution | Accepted | Include the SRD 5.1 attribution statement specified in the project brief in both `README.md` and `NOTICE`. Do not add non-SRD official D&D text or artwork. Keep SRD terms distinct from the project's Apache-2.0 license. |
| ADR-009 | Image publish/release flow | Open — document discrepancy | The build brief describes building/pushing version-tagged images on `v*`; architecture v4 says publish commit-SHA images on `main`, then promote/re-tag the same image for a version release. Prefer build-once/promote-same-digest, but confirm with the human before enabling CD. |
| ADR-010 | Maven coordinates and Java root package | Accepted | Use Maven `groupId` `com.github.sbadolankaur`, `artifactId` `tabletop-rpg-simulator`, and Java root package `com.github.sbadolankaur.rpg`. Keep the artifact ID unchanged. |
| ADR-011 | GitHub owner, repository name, and visibility | Accepted | Keep the public repository `SamarthBadola/Tabletop-RPG-Simulator`. |
| ADR-012 | Developer tooling baseline | Accepted | Local versions reported by the project owner: Git `2.54.0.windows.1`; Temurin Java `25.0.4.1+1`; Docker Engine and CLI `29.8.2` (Linux engine, `desktop-linux` context); Docker Compose `5.5.1`; Docker Buildx `0.37.2`. The Docker daemon was reported as running. Use Docker Desktop's supplied Compose and Buildx plugins. |
| ADR-013 | Main-branch governance | Active; cleanup and behavior verification pending | Three active rulesets target the default branch. `main-quality-gates` requires a PR, resolved review conversations, the `repo-hygiene`, `secrets`, and `DCO` status checks, linear history, and blocks deletion/force-push; it has no bypass actors and sets the required review count to 0 because review approval is handled separately. `main-review-policy` requires one approval, dismisses stale approvals, and allows Repository administrators to bypass this review policy for pull requests only. Its live configuration still includes deletion and non-fast-forward rules; remove those so it is review-only. `main-update-access` restricts updates and includes deletion/non-fast-forward protections, with Repository administrators set to Always allow bypass for the access rule. A direct-push attempt and the full merge/bypass behavior have not yet been verified end-to-end. See `docs/REPOSITORY-GOVERNANCE.md`. |
| ADR-014 | DCO policy | Configured and checked on PR #4 | Use the DCO GitHub App with `.github/dco.yml` set to `require.members: false`. The DCO check ran and passed on owner-authored PR #4, and `DCO` is a required status check in `main-quality-gates`. Non-owner contributors must include a matching `Signed-off-by` trailer on each applicable commit. The config file alone would not run the App; installation/check execution has been observed. |
| ADR-015 | Durable AI handoff | Accepted | `docs/AI-HANDOFF.md` records the verified repository state, source-of-truth documents, constraints, unresolved decisions, and next milestone so future contributors or AI assistants can continue without depending on chat memory. Keep this file and `docs/PROGRESS.md` aligned with the repository after each meaningful milestone. |

## Verified toolchain references (baseline recorded 2026-10-11)

- Java: Temurin 25 LTS, local runtime version supplied by the project owner: `25.0.4.1+1`.
- Spring Boot: `4.1.1`, selected baseline.
- Maven: `3.10.0` for the Maven Wrapper distribution.
- PostgreSQL: `16.15` initial baseline, using PostgreSQL major version 16.
- CMake: `4.4.4`, selected baseline for local C++ pathfinder development when it begins.
- Git: `2.54.0.windows.1`, as reported locally.
- Docker Engine and CLI: `29.8.2`; the owner reported the Linux engine running under Docker Desktop with context `desktop-linux`.
- Docker Compose: `5.5.1`, supplied by Docker Desktop.
- Docker Buildx: `0.37.2`, supplied by Docker Desktop.

## Dependency/version policy

Keep dependency versions exact in `pom.xml`, the Maven Wrapper configuration, Docker base-image tags, and other build manifests. Confirm releases against official documentation before pinning them. Use reviewed Dependabot updates rather than floating project dependencies to `latest`.

## Known open decisions

- Resolve ADR-009 before creating release automation.
- Follow the build brief's Step 1–5 order. The ruleset specification is not written; do not implement game mechanics from memory.
- The brief calls for additional human decisions later, including tier-2 spells and SRD monster lists, surprise handling, DM overrides, corner-cutting behavior, final upload limits, and journal event capture. Ask when the corresponding design stage is reached rather than guessing early.
