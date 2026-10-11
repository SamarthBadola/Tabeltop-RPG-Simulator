# Progress Tracker

## Current milestone: Repository bootstrap and toolchain baseline

- [x] Read the AI build brief and architecture v4.
- [x] Define target monorepo structure and repository hygiene conventions.
- [x] Add project-specific `.gitignore`, `.gitattributes` and `.editorconfig`.
- [x] Add contribution/security guidance and initial decision log.
- [x] Add a bootstrap CI workflow (repository checks + secret scanning).
- [x] Add weekly Dependabot configuration for GitHub Actions.
- [x] Merge the repository-bootstrap pull request.
- [x] Verify bootstrap CI passed on the pull request and on `main`.
- [ ] Confirm GitHub Actions with read-only default permissions and protect `main` with the available bootstrap status checks.
- [x] Record Java 25, Spring Boot 4.1.1, Maven 3.10.0, PostgreSQL 16.15, and local Git/Docker/Compose/Buildx versions.
- [x] Confirm repository name `SamarthBadola/Tabletop-RPG-Simulator` and record Maven group ID, artifact ID, and Java root package.
- [x] Verify the local Docker daemon, Compose plugin, Buildx, and Git versions.
- [ ] Confirm whether public repository visibility is intentional; select the project's own-code license and add verbatim SRD 5.1 attribution.
- [ ] Build the walking skeleton: Maven wrapper, Spring Boot health endpoints, PostgreSQL/Flyway, Compose, app Dockerfile and pathfinder skeleton.
- [ ] Add Maven Dependabot once `app/pom.xml` exists; add Docker Dependabot entries once the respective Dockerfiles exist.
- [ ] Expand CI to unit, integration, pathfinder, contract, secrets and Compose smoke checks.
- [ ] Add digest-preserving GHCR publish/release workflows after the CD discrepancy is resolved.

## Working agreement

Work in small tasks. For each task, state its done-when, run the relevant checks, commit with a Conventional Commit message, and report exact results including failures.
