# Contributing

## Branches

Create short-lived branches from an up-to-date `main`:

- `feat/<short-description>` — new capability
- `fix/<short-description>` — bug fix
- `test/<short-description>` — tests
- `refactor/<short-description>` — behavior-preserving refactor
- `docs/<short-description>` — documentation
- `ci/<short-description>` — CI/CD and repository automation
- `chore/<short-description>` — maintenance

Do not commit directly to `main` once repository protections are enabled.

## Commit messages

Use Conventional Commits:

`<type>(<optional-scope>): <imperative summary>`

Examples:

- `chore(repo): establish repository governance`
- `ci: add Java unit-test workflow`
- `test(engine): cover dice parser limits`
- `feat(engine): parse keep-highest dice modifiers`
- `docs: record release-publishing decision`

Prefer one demonstrable task per commit. Do not bundle unrelated formatting, refactoring, and feature work.

## Pull requests

- Explain the purpose and user-visible or architectural impact.
- Link the relevant issue/task when one exists.
- Include how the change was tested and the actual result.
- Add regression tests for bug fixes; engine tests are test-first.
- Keep migrations additive and check a fresh database.
- Do not add secrets, real environment files, unlicensed SRD material, or official artwork.
- Do not claim checks passed unless they actually ran and passed.

All required CI checks must pass before merge. Use squash merge so `main` retains a linear history.
