# Progress Tracker

## Current milestone: Repository bootstrap

- [x] Read the AI build brief and architecture v4.
- [x] Define target monorepo structure and repository hygiene conventions.
- [x] Add project-specific `.gitignore`, `.gitattributes` and `.editorconfig`.
- [x] Add contributing/security guidance and initial decision log.
- [x] Add a bootstrap CI workflow (repository checks + secret scanning).
- [x] Add weekly Dependabot configuration.
- [ ] Merge the repository-bootstrap pull request.
- [ ] Enable GitHub Actions with read-only default permissions.
- [ ] Protect `main` after the relevant status checks have run at least once.
- [ ] Confirm GitHub owner, repository name, group ID/root package, exact Spring Boot/Maven versions, software license and SRD attribution.
- [ ] Build the walking skeleton: Maven wrapper, Spring Boot health endpoints, PostgreSQL/Flyway, Compose, app Dockerfile and pathfinder skeleton.
- [ ] Expand CI to unit, integration, pathfinder, contract, secrets and Compose smoke checks.
- [ ] Add digest-preserving GHCR publish/release workflows after the CD discrepancy is resolved.

## Working agreement

Work in small tasks. For each task, state its done-when, run the relevant checks, commit with a Conventional Commit message, and report exact results including failures.
