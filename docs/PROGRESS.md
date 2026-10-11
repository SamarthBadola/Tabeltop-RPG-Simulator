# Progress Tracker

Last reconciled: 2026-10-12.

## Current milestone: Repository bootstrap complete; Step 1 not started

The repository is prepared for implementation, but the application itself is not yet scaffolded. Treat an item as complete only when the relevant artifact exists and its stated verification has actually been performed.

### Repository bootstrap and governance

- [x] Read the AI build brief and architecture v4.
- [x] Define the target monorepo structure and repository hygiene conventions.
- [x] Add project-specific `.gitignore`, `.gitattributes`, and `.editorconfig`.
- [x] Add contribution and security guidance, pull-request template, decision log, progress tracker, and repository-governance guide.
- [x] Add bootstrap CI with `repo-hygiene` and `secrets` jobs.
- [x] Verify both bootstrap CI jobs passed on the pull request and on `main`.
- [x] Confirm GitHub Actions uses read-only repository permissions for the bootstrap workflow.
- [x] Add weekly Dependabot checks for GitHub Actions.
- [x] Record repository name/owner, public visibility, Maven coordinates, and Java root package.
- [x] Record the selected Java 25, Spring Boot 4.1.1, Maven 3.10.0, and PostgreSQL 16.15 baseline, plus locally reported Git/Docker/Compose/Buildx versions.
- [x] Keep project-owned code and documentation under Apache-2.0.
- [x] Add the SRD 5.1 CC-BY-4.0 attribution to `README.md` and `NOTICE`.
- [x] Add `.github/CODEOWNERS` for the repository owner.
- [x] Add DCO guidance and `.github/dco.yml`.
- [x] Verify the DCO App check passed on PR #4.
- [x] Confirm `main-quality-gates`, `main-review-policy`, and `main-update-access` are active and target the default branch.
- [x] Confirm `main-quality-gates` has no bypass actors and requires `repo-hygiene`, `secrets`, and `DCO`.
- [x] Separate the one-review policy into `main-review-policy`, with the repository-administrator bypass limited to pull requests.
- [x] Record the current live ruleset state and remaining cleanup in `docs/REPOSITORY-GOVERNANCE.md`.
- [ ] In GitHub Settings, remove the deletion and non-fast-forward rules from `main-review-policy`; keep this ruleset focused on the one-approval review policy.
- [ ] Behaviorally verify direct-push rejection, normal-contributor review enforcement, and that required checks cannot be bypassed by the administrator.
- [ ] Confirm the owner-only merge restriction remains accurate if additional administrators/collaborators are ever added.

### Step 0 — decisions required before implementation

- [x] Confirm repository name and owner: `SamarthBadola/Tabletop-RPG-Simulator`.
- [x] Record Maven group ID `com.github.sbadolankaur`, artifact ID `tabletop-rpg-simulator`, and Java root package `com.github.sbadolankaur.rpg`.
- [x] Select and record the baseline Java, Spring Boot, Maven, and PostgreSQL versions.
- [x] Add the SRD 5.1 attribution statement to `README.md` and `NOTICE`.
- [ ] Resolve the build brief/architecture discrepancy about publishing and releasing container images before implementing CD. See ADR-009.

### Step 1 — walking skeleton (next implementation milestone)

**Status: not started.** Do not mark this step complete until the done-when below is demonstrated on a fresh clone and CI.

- [ ] Create the Maven application project and Maven Wrapper targeting Maven 3.10.0.
- [ ] Create the Spring Boot application with health and readiness endpoints.
- [ ] Configure PostgreSQL and establish the baseline Flyway migration.
- [ ] Implement registration/login; the first registered account becomes the instance administrator.
- [ ] Add the application Dockerfile.
- [ ] Add Compose files for the runtime and local source builds, a safe `.env.example`, and `scripts/init-env.sh`.
- [ ] Add the pathfinder deployment skeleton needed by the planned stack; the application must be able to run with the Java pathfinder and without the C++ service.
- [ ] Expand CI beyond repository hygiene/secrets to the application test and integration checks applicable at this stage.
- [ ] Add the Compose-based smoke test and README quick start.
- [ ] Demonstrate the Step 1 done-when: on a fresh clone, run the environment initialization and Compose startup, register the first account at `http://localhost:8080`, and get a green pull-request CI run.

### Later build milestones

- [ ] Step 2 — Dice engine: grammar, limits, `RandomSource`, `RollResult`; parser, property, and uniformity tests.
- [ ] Step 3 — Kernel and architecture tests: ability/stat/modifier types, durations, active ArchUnit rules, and tests that prove intentional violations fail.
- [ ] Step 4 — Campaign, session, and access: migrations, invite flow, per-campaign authorization, 404 isolation, and inactive-session action rejection.
- [ ] Step 5 — Content-pack skeleton: definition tables, JSON schema validation, immutable in-memory loading, attribution page, and empty SRD pack manifest. No game data yet.
- [ ] Stop after Step 5 until the ruleset, content schema, grid/geometry, and UX/API/threat-model specifications are supplied and resolved.

## CI status snapshot

The current `.github/workflows/ci.yml` contains only:
- `repo-hygiene`: whitespace checks and required-document/policy-file checks.
- `secrets`: Gitleaks scan of the full Git history.

Both passed on the latest recorded `main` run, [CI run #15](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38167905851). The DCO App check passed on [PR #4](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/pull/4).

Application unit tests, Testcontainers integration tests, C++ pathfinder tests, contract tests, Compose smoke tests, and release publishing are not implemented or verified.

## Working agreement for the next task

1. Read `docs/AI-HANDOFF.md`, this tracker, `docs/DECISION-LOG.md`, `docs/REPOSITORY-GOVERNANCE.md`, the full build brief, and architecture v4 before coding.
2. Work in one focused PR at a time, from a short-lived branch. Use Conventional Commits and keep `main` untouched.
3. For each task, state a verifiable done-when, run the relevant checks, report actual results, and update this tracker in the same PR.
4. Never mark work done from a file-existence check alone when the required behavior must be demonstrated.
5. Do not implement D&D rules from memory. Ask the human when a required specification is missing or conflicting.
