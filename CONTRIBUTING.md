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

Do not commit directly to `main`. All proposed project changes must be submitted through a pull request. The repository maintainer, [@SamarthBadola](https://github.com/SamarthBadola), is the only person authorized to merge changes into `main`. Community contributors can use forks and submit pull requests without being granted write access to the repository.

The GitHub branch rulesets must enforce this policy; follow [`docs/REPOSITORY-GOVERNANCE.md`](docs/REPOSITORY-GOVERNANCE.md) and do not treat it as technically enforced until both rulesets are Active.

## Project license and contribution terms

Project-owned code and documentation are licensed under the Apache License 2.0; see [LICENSE](LICENSE). This is a permissive open-source license that permits use, modification, and redistribution subject to its terms. It includes an express patent license.

By intentionally submitting a contribution for inclusion (including through a pull request), you offer that contribution under Apache-2.0, consistent with Section 5 of the license, unless a separate written agreement says otherwise. You retain copyright in your contribution. Do not submit material that you do not have the right to contribute under these terms. A Contributor License Agreement (CLA) is not currently required.

The Apache license applies to project-owned material, not automatically to SRD 5.1 content, external dependencies, or third-party assets. SRD material must remain properly attributed and used under its own CC-BY-4.0 terms. Do not add unlicensed D&D material or official artwork.

## DCO sign-off

The repository uses the Developer Certificate of Origin (DCO) to record that contributors have the right to submit their changes under the project's open-source license. The DCO check is provided by the [DCO GitHub App](https://github.com/apps/dco).

Every commit from a non-owner contributor in a pull request must include a valid `Signed-off-by` trailer, and the trailer email must match that commit's author email. In your local Git configuration, set the name and email you intend to use for commits:

```bash
git config user.name "Your Name"
git config user.email "the-email-used-for-your-commits"
git commit -s -m "feat: describe your change"
```

The `-s` option adds the sign-off trailer to that commit. Check the result before pushing:

```bash
git show -s --format=%B HEAD
```

The commit message should end with a line like `Signed-off-by: Your Name <your-email@example.com>`. Repeat `git commit -s` for every commit you create. A pull request fails the DCO check if any applicable commit is missing a valid sign-off. If you have already made unsigned commits, amend or interactively rebase your own commits to add sign-offs before opening/updating the PR; do not sign off on work you are not authorized to certify.

The repository configuration exempts the repository owner from sign-off on a personal repository; it does not exempt community contributors. DCO is a provenance and contribution-rights check, not a substitute for code review, tests, or security checks.

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

All required CI checks must pass before merge. The repository owner reviews proposed changes and is the only person authorized to merge pull requests into `main`. Use squash merge so `main` retains a linear history.
