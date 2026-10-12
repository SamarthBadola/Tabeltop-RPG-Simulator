# Progress Tracker

Last reconciled: 2026-10-12.

## Current milestone: Step 1 — walking skeleton (CI verified; local acceptance pending)

The walking skeleton was merged in PR #6. CI and repository-maintenance improvements were merged in PRs #9 and #10. The five GitHub Actions CI jobs passed on both [PR run #40](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38251836058) and [post-merge `main` run #41](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38252435012). Keep Step 1 open until the project owner performs and records the fresh-clone startup test on Docker Desktop.

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
- [x] Remove the deletion and non-fast-forward rules from \`main-review-policy\`; the live ruleset now contains only the pull-request/review policy.
- [ ] Behaviorally verify direct-push rejection, normal-contributor review enforcement, and that an administrator cannot bypass the quality gates.
- [ ] Resolve the build brief/architecture image-publishing discrepancy (ADR-009) before implementing release automation.

### Step 1 — walking skeleton

The implementation is merged on \`main\`. CI verification is complete; the owner-run local first-start acceptance test remains pending.

- [x] Create the Maven project with fixed coordinates and the Maven Wrapper configuration.
- [x] Create the Spring Boot app, login/registration controllers, and health/readiness configuration.
- [x] Add a PostgreSQL schema migration and Flyway configuration.
- [x] Implement serialized first-account registration: only the first account becomes the instance administrator; later public registration is closed pending invites.
- [x] Implement BCrypt password storage, validation, Spring Security form login, CSRF, session-cookie settings, restrictive CSP, and basic bounded login throttling.
- [x] Add the application container image and Compose configuration with private PostgreSQL and a named database volume.
- [x] Add a secret-generating environment initializer and a clean-stack smoke script.
- [x] Add unit tests and Testcontainers-backed integration tests for registration, login, CSRF, readiness, and login throttling.
- [x] Add CI jobs for formatting/unit tests, PostgreSQL integration tests, secret scanning, repository checks, and source-built Compose smoke on pull requests and branch pushes.
- [x] Update README local startup steps and document module setup.
- [x] Verify \`spotless:check test\` and \`verify -Pintegration\` pass in GitHub Actions on PR run #40 and post-merge run #41.
- [x] Verify the Compose smoke job passes on PR run #40 and post-merge \`main\` run #41.
- [x] Verify the active \`main-quality-gates\` ruleset requires \`repo-hygiene\`, \`secrets\`, \`DCO\`, \`unit\`, \`integration\`, and \`compose-smoke\`.
- [ ] On a fresh clone, run environment initialization, start the source-built Compose stack, register the first account at \`http://localhost:8080\`, sign in, and record the outcome.
- [ ] Reconcile this tracker and the README/handoff with the result of the local startup acceptance test. Do not mark Step 1 complete until the result is recorded.

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

The active \`.github/workflows/ci.yml\` defines \`repo-hygiene\`, \`unit\`, \`integration\`, \`secrets\`, and \`compose-smoke\`. All five jobs passed on [PR run #40](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38251836058) and [post-merge run #41 on \`main\`](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38252435012). Compose smoke runs on pull requests and branch pushes after integration tests; it is not main-only. The live \`main-quality-gates\` ruleset requires all five jobs plus \`DCO\`.

The first Dependabot Compose scan succeeded after PR #10 changed the \`/deploy\` ecosystem to \`docker-compose\`. Dependabot then proposed PostgreSQL 18.6 in PR #11, but its source-built Compose smoke test failed because the PostgreSQL 18 image expects a different volume layout. A major-version ignore is being added for PostgreSQL in this maintenance PR. Do not merge PR #11 as-is.

## Working agreement

1. Read \`docs/AI-HANDOFF.md\`, this tracker, \`docs/DECISION-LOG.md\`, \`docs/REPOSITORY-GOVERNANCE.md\`, the full build brief, and architecture v4 before coding.
2. Work one focused PR at a time from a short-lived branch. Use Conventional Commits and do not push directly to \`main\`.
3. Keep code efficient and simple. Prefer clear names over redundant comments; document public contracts and non-obvious constraints only.
4. Define observable done-when criteria, run the relevant tests, report actual outcomes, and update docs in the same PR.
5. Never claim a test passed unless the corresponding job actually passed.
