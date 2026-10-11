# AI and Contributor Handoff

Last reconciled: 2026-10-12. This document is a durable snapshot of verified repository state and the next work boundary. Re-check live GitHub settings and current files before relying on details that can change.

## Read these sources first

Before making code changes, read the following files from the current branch in this order:

1. `docs/AI-HANDOFF.md` — this continuity snapshot and next task.
2. `docs/PROGRESS.md` — milestone checklist and current implementation status.
3. `docs/DECISION-LOG.md` — accepted decisions and unresolved decisions.
4. `docs/REPOSITORY-GOVERNANCE.md` — live branch-ruleset snapshot, intended behavior, and outstanding verification.
5. `docs/Tabletop RPG Simulator AI Build Brief.md` — detailed constraints, architecture rules, testing rules, and build order.
6. `docs/architecture.md` — architecture v4; consult the relevant sections before implementation.
7. `README.md`, `CONTRIBUTING.md`, `SECURITY.md`, `NOTICE`, `LICENSE`, and `.github/workflows/ci.yml` as relevant to the task.

These documents complement one another. A more specific approved specification takes precedence over the high-level build brief. If source documents disagree or leave an implementation-critical choice unresolved, report the discrepancy and ask the human; do not silently choose a behavior or derive D&D mechanics from memory. Update the decision log and progress tracker in the same PR as the decision or implementation they describe.

## Project identity and fixed baseline

- Repository: public GitHub repository `SamarthBadola/Tabletop-RPG-Simulator`.
- Project: self-hosted, browser-based D&D 5e SRD 5.1 (2014 rules) character manager and encounter runner. It is not a virtual tabletop.
- Team: two-person course project; the owner is the maintainer. Do not infer or invent teammate GitHub handles.
- Maven `groupId`: `com.github.sbadolankaur`.
- Maven `artifactId`: `tabletop-rpg-simulator`.
- Java root package: `com.github.sbadolankaur.rpg`.
- Chosen baseline: Temurin Java 25, Spring Boot 4.1.1, Maven Wrapper distribution 3.10.0, PostgreSQL 16.15 (PostgreSQL major 16).
- Locally reported developer tooling: Git `2.54.0.windows.1`; Java `25.0.4.1+1`; Docker Engine/CLI `29.8.2`; Docker Compose `5.5.1`; Docker Buildx `0.37.2`. The owner reported the Linux Docker engine active under the `desktop-linux` context. These are reported local versions, not CI runner versions.
- Project-owned code/docs use Apache-2.0. SRD 5.1 material has separate CC-BY-4.0 terms and attribution in `README.md` and `NOTICE`.
- GitHub Actions and GHCR are the planned CI/CD and registry platforms. The current workflow only provides repository hygiene and secret scanning.

## Verified current implementation status

**Repository bootstrap is complete; the Step 1 application walking skeleton is not started.**

The current `main` contains repository policy/docs and README placeholders in `app/`, `deploy/`, and `pathfinder/`. There is currently no `app/pom.xml`, Maven Wrapper, Spring Boot source, Flyway migration, authentication flow, app/pathfinder Dockerfile, Compose file, environment initializer, or smoke test. Do not claim the application runs.

Current CI workflow `.github/workflows/ci.yml` contains:
- `repo-hygiene`: patch whitespace and repository-policy/documentation presence checks.
- `secrets`: Gitleaks scan of repository history.

Both jobs passed on the latest recorded `main` run, [run #15](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/actions/runs/38167905851). The DCO App check passed on [PR #4](https://github.com/SamarthBadola/Tabletop-RPG-Simulator/pull/4). No app unit tests, integration tests, pathfinder tests, contract tests, Compose smoke tests, or release publishing are implemented/verified.

## GitHub governance snapshot

Three active repository rulesets target the default branch:

- `main-quality-gates`: require PR; required approving-review count 0; require resolved conversations, linear history, deletion protection, non-fast-forward protection; require `repo-hygiene`, `secrets`, and `DCO`; bypass list empty.
- `main-review-policy`: require PR and one approving review; dismiss stale approvals; Repository administrators have `For pull requests only` bypass. **Known cleanup:** this ruleset also contains deletion and non-fast-forward rules; remove them in GitHub Settings so it is review-only.
- `main-update-access`: restrict updates and protect against deletion/non-fast-forward updates; Repository administrators have `Always allow` bypass in this access-only ruleset. This exception was used to resolve a protected-ref merge failure.

The intent is for the no-bypass quality layer to prevent an administrator from bypassing CI/security requirements or pushing directly. The rulesets' Active state and required-check configuration have been verified via GitHub, but the rejection of direct pushes and all edge cases have not been empirically demonstrated. See `docs/REPOSITORY-GOVERNANCE.md` before changing settings.

## Non-negotiable engineering constraints

- Do not implement D&D mechanics from memory. The ruleset specification is not yet written.
- Until required specs exist, focus on infrastructure, dice engine foundations, kernel types, schemas, authentication, and CI in the order stated in the build brief.
- Keep `engine` pure JDK: no Spring, JPA, HTTP, or filesystem I/O imports.
- Preserve the specified layering and ArchUnit rules. Do not cross from one feature's web layer into another feature's persistence layer; avoid feature-package cycles.
- Keep I/O outside database write transactions. Use additive Flyway migrations and never edit a migration that has been applied.
- Security by default: deny-by-default routes, authorization at the data layer, input validation, CSRF protections, and no secrets in files/logs. Treat imports/uploads as untrusted.
- Use tests that prove behavior. Never weaken/skip/rewrite tests to get green CI; report failures honestly.
- Prefer existing dependencies and simple designs. Justify any new dependency and ask the human before introducing it.
- Use exact pinned dependency/base-image versions, and verify APIs/versions against appropriate current documentation.
- Use short-lived branches, Conventional Commits, PRs, and squash merges. Do not commit directly to `main`.
- Keep progress/decision docs in sync with implementation; a checkbox is not proof unless its done-when was demonstrated.

## Next sequence of work

The current task is the **repository-record reconciliation PR**. After it passes CI and is merged:

1. Refresh the local/current checkout from `main`. Re-read this handoff and all source docs; do not assume the branch still matches if another PR has merged.
2. Start one separate PR for **Step 1 — walking skeleton**. Keep its scope focused on a minimal, runnable Spring Boot + PostgreSQL/Flyway + auth + Docker Compose baseline, plus the required app CI.
3. The Step 1 acceptance test from the build brief is the source of truth: on a fresh clone, run the environment initialization script, start Compose, register the first account at `http://localhost:8080`, and demonstrate green CI on the PR.
4. Include meaningful tests and update `docs/PROGRESS.md`, `docs/DECISION-LOG.md`, and README status in the same implementation PR.
5. Do not skip ahead to game mechanics or the later milestones until their prerequisite specs and previous-step acceptance tests are done.

## Open questions that must not be silently resolved

- ADR-009: the build brief says publish version-tagged images on `v*`, while architecture v4 says build/publish images by commit SHA on `main` then promote the same digest to release tags. Ask the human before enabling CD; preferred direction is build once and promote the same digest.
- Additional SRD content decisions remain for the appropriate future milestone: spell and monster subsets, surprise handling, DM override display, corner-cutting default, final upload limits, and journal event capture.
- The ruleset specification, content schema specification, grid/geometry specification, and UX/API specification with threat model are not yet authored. Stop after build brief Step 5 and ask for these before continuing into the later feature milestones.
