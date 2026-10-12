# Progress Tracker

Last reconciled: 2026-10-12.

## Current milestone: Step 1 — walking skeleton (implementation PR in progress)

Repository bootstrap and documentation reconciliation are complete and merged. The current implementation branch is \`feat/walking-skeleton\`. Do not mark Step 1 complete until a clean build, integration checks, and a fresh-clone Compose acceptance test have been demonstrated. Update this tracker in the implementation PR after actual CI results are known.

### Completed repository bootstrap and governance

- [x] Read the AI build brief and architecture v4.
- [x] Define the monorepo structure and repository hygiene conventions.
- [x] Add \`.gitignore\`, \`.gitattributes\`, \`.editorconfig\`, contribution/security guidance, PR template, decision log, progress tracker, and governance guide.
- [x] Add bootstrap CI with \`repo-hygiene\` and \`secrets\`.
- [x] Verify bootstrap CI on PRs and \`main\`.
- [x] Confirm GitHub Actions uses read-only repository permissions for CI.
- [x] Add weekly Dependabot checks for GitHub Actions.
- [x] Record repository identity, Maven coordinates, Java package and selected baseline versions.
- [x] Select Apache-2.0 for project-owned code/documentation.
- [x] Record SRD 5.1 attribution in \`README.md\` and \`NOTICE\`.
- [x] Add \`.github/CODEOWNERS\`, DCO guidance and \`.github/dco.yml\`.
- [x] Verify the DCO App check passed on PR #4.
- [x] Confirm \`main-quality-gates\`, \`main-review-policy\`, and \`main-update-access\` are Active.
- [x] Record live ruleset configuration and remaining cleanup in \`docs/REPOSITORY-GOVERNANCE.md\`.
- [ ] In GitHub Settings, remove the deletion and non-fast-forward rules from \`main-review-policy\`.
- [ ] Behaviorally verify direct-push rejection, normal-contributor review enforcement, and that an administrator cannot bypass the quality gates.
- [ ] Resolve the build brief/architecture image-publishing discrepancy (ADR-009) before implementing release automation.

### Step 1 — walking skeleton

Initial implementation is in \`feat/walking-skeleton\`; implementation and CI review are in progress.

- [x] Create the Maven project with fixed coordinates and the Maven Wrapper configuration.
- [x] Create the Spring Boot app, login/registration controllers, and health/readiness configuration.
- [x] Add a PostgreSQL schema migration and Flyway configuration.
- [x] Implement serialized first-account registration: only the first account becomes the instance administrator; later public registration is closed pending invites.
- [x] Implement BCrypt password storage, validation, Spring Security form login, CSRF, session-cookie settings, restrictive CSP, and basic bounded login throttling.
- [x] Add the application container image and Compose configuration with private PostgreSQL and a named database volume.
- [x] Add a secret-generating environment initializer and a clean-stack smoke script.
- [x] Add unit tests and Testcontainers-backed integration tests for registration, login, CSRF, readiness, and login throttling.
- [x] Expand CI to run formatting/unit tests, PostgreSQL integration tests, secret scanning, repository checks, and main-branch Compose smoke.
- [x] Update README local startup steps and document module setup.
- [ ] Verify \`spotless:check test\` and \`verify -Pintegration\` pass in GitHub Actions.
- [ ] Verify the Compose smoke job passes on \`main\` after merge.
- [ ] On a fresh clone, run environment initialization, start the source-built Compose stack, register the first account at \`http://localhost:8080\`, and sign in.
- [ ] Add \`unit\` and \`integration\` as required status checks in the live \`main-quality-gates\` ruleset after the jobs have reported successful checks. Current required checks are \`repo-hygiene\`, \`secrets\`, and \`DCO\`; rulesets must be updated in GitHub Settings.
- [ ] Reconcile the docs once actual CI and fresh-clone behavior are verified. Do not treat files existing as proof that runtime behavior passes.

### Step 2 — dice engine (after Step 1 is complete)

- [ ] Define the dice-expression grammar and limits from project specifications.
- [ ] Implement \`RandomSource\`, parser, and immutable \`RollResult\`.
- [ ] Add parser, malformed/over-limit rejection, range/property, keep/drop, and seeded uniformity tests.

### Step 3 — kernel and architecture tests

- [ ] Implement the kernel types and modifier semantics with specified order/rounding.
- [ ] Add ArchUnit rules and demonstrate that deliberate violations fail the build.

### Step 4 — campaign, session, and access

- [ ] Add campaign/member/invite/session/scene/game-clock/audience schema and service flows.
- [ ] Test invite enforcement, cross-campaign isolation (foreign resource is 404), and inactive-session rejection.

### Step 5 — content-pack skeleton

- [ ] Add content-pack and definition tables, JSON schema validation, immutable in-memory loading, an attribution page, and an empty SRD pack manifest.
- [ ] No SRD game data yet.

**Stop after Step 5.** Ask for the ruleset specification, content schema specification, grid/geometry specification, and UX/API specification with threat model before implementing later gameplay features. Do not invent D&D rules from memory.

## CI status

The \`.github/workflows/ci.yml\` source now defines \`repo-hygiene\`, \`unit\`, \`integration\`, \`secrets\`, and a \`main\`-only \`compose-smoke\` job. The PR run is the source of truth for the jobs that execute on the PR; Compose smoke is gated to pushes on \`main\`. Update this section after checking the current run URLs and job conclusions.

## Working agreement

1. Read \`docs/AI-HANDOFF.md\`, this tracker, \`docs/DECISION-LOG.md\`, \`docs/REPOSITORY-GOVERNANCE.md\`, the full build brief, and architecture v4 before coding.
2. Work one focused PR at a time from a short-lived branch. Use Conventional Commits and do not push directly to \`main\`.
3. Keep code efficient and simple. Prefer clear names over redundant comments; document public contracts and non-obvious constraints only.
4. Define observable done-when criteria, run the relevant tests, report actual outcomes, and update docs in the same PR.
5. Never claim a test passed unless the corresponding job actually passed.
