# Decision Log

Record non-obvious choices here. A decision should state its status, context, choice, and consequences. Update this log in the same PR as the implementation that establishes the decision.

| ID | Topic | Status | Decision / note |
|---|---|---|---|
| ADR-001 | Repository topology | Accepted | One monorepo: `app/`, `pathfinder/`, `deploy/`, `docs/`, `.github/`. |
| ADR-002 | Application runtime | Accepted baseline | Java 21 (Temurin), Maven Wrapper, Spring Boot; pin exact Maven and Spring Boot versions before generating the app. |
| ADR-003 | Persistence | Accepted | PostgreSQL 16 and Flyway; never use H2. |
| ADR-004 | CI provider and registry | Accepted | GitHub Actions and GitHub Container Registry (GHCR). |
| ADR-005 | Git workflow | Accepted | Short-lived branches, Conventional Commits, PR checks, squash merge, linear `main`. |
| ADR-006 | Action pinning | In progress | Prefer verified, full-length commit SHA pins for third-party Actions; add a readable release-version comment and use Dependabot for updates. |
| ADR-007 | Software license | Open | Select a license for the project's own code before adding a root `LICENSE`. This is separate from SRD content licensing. |
| ADR-008 | SRD attribution | Open | Copy the required SRD 5.1 attribution notice verbatim from the authoritative SRD source into `NOTICE`; do not paraphrase or invent it. |
| ADR-009 | Image publish/release flow | Open — document discrepancy | The build brief describes building/pushing version-tagged images on `v*`; architecture v4 says publish commit-SHA images on `main`, then promote/re-tag the same image for a version release. Prefer build-once/promote-same-digest, but confirm before enabling CD. |
| ADR-010 | Java group ID / root package | Open | Replace `com.yourteam.rpg` placeholder with the team's real group ID and root package before generating the Maven project. |
| ADR-011 | GitHub owner and repository name | Open | Needed for GHCR image names and final Compose configuration. |

## Dependency/version decisions

Do not treat versions in examples as final without checking official documentation and recording the exact selected versions. The current Java baseline is 21; Spring Boot and Maven Wrapper versions must be verified when the walking skeleton is generated.
