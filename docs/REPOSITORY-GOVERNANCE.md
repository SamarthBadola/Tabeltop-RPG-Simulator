# Repository Governance and Main-Branch Protection

This repository is public. Community contributions are welcome, but only the repository owner, [@SamarthBadola](https://github.com/SamarthBadola), should merge pull requests into `main`. These instructions describe the required GitHub settings; a policy written in the repository does not itself enforce branch restrictions.

## Why two rulesets are used

Use two separate rulesets targeting the default branch, `main`. Separating access control from quality gates prevents the administrator's pull-request-only exception for update access from accidentally bypassing CI and review requirements.

### 1. `main-quality-gates`

In **Settings → Rules → Rulesets → New branch ruleset**:

- Name: `main-quality-gates`
- Enforcement status: **Active**
- Target branches: include the default branch (`main`)
- Require a pull request before merging.
- Require **1 approving review**.
- Dismiss stale approvals when new commits are pushed.
- Require all conversations to be resolved.
- Require status checks to pass: `repo-hygiene`, `secrets`, and the DCO App's status check (select the exact check context displayed by GitHub, typically `DCO`). Add the DCO check after installing the App and after it has reported a check on a pull request.
- Require linear history.
- Block force pushes and restrict deletion.
- **Do not add a bypass actor** to this ruleset.

The latest commit on a PR must pass both required checks. For changes written by the owner, the teammate can supply the required approval; only the owner merges. Keep branch-protection checks configured after the full build checks are added to CI.

### 2. `main-update-access`

Create another active branch ruleset targeting `main`:

- Name: `main-update-access`
- Enforcement status: **Active**
- Enable **Restrict updates**.
- Block force pushes and restrict deletion.
- Under **Bypass list**, add the **Repository administrators** role, then change its bypass mode from **Always allow** to **For pull requests only**.

This combination allows the repository administrator to merge a pull request while preventing a direct push bypass. The first ruleset remains active separately, so its required approval and status checks still apply. Keep the number of repository administrators limited to the owner.

GitHub rulesets are available for public repositories on the Free plan. The "For pull requests only" bypass mode is documented by GitHub and preserves a PR trail while preventing direct pushes by that bypass actor:
https://docs.github.com/en/repositories/configuring-branches-and-merges-in-your-repository/managing-rulesets/creating-rulesets-for-a-repository

## CODEOWNERS and the two-person team

`.github/CODEOWNERS` assigns the repository owner to all files so GitHub can request review. A code owner cannot approve their own pull request. If code-owner approval is later made mandatory, add Nihchal's verified GitHub username as a second code owner first, and confirm the rule works for PRs authored by either teammate. Do not guess the username.

## DCO sign-off check

1. Install the [DCO GitHub App](https://github.com/apps/dco) for `SamarthBadola/Tabletop-RPG-Simulator`. Grant it access to this repository only; review the App's permissions before installing.
2. The repository contains `.github/dco.yml` with `require.members: false`. Because this is a personal repository, the DCO App documents this setting as allowing the repository owner to push commits without a sign-off. It does **not** exempt outside contributors.
3. Open a test pull request. Confirm the DCO App reports a successful check for signed-off commits, and reports failure for a commit missing a matching `Signed-off-by` trailer.
4. Edit the existing `main-quality-gates` ruleset and require that DCO check alongside `repo-hygiene` and `secrets`. Do **not** create another ruleset for DCO.
5. Confirm the DCO status context is visible among required checks and that a failing DCO result prevents merging.

The DCO App is a third-party GitHub App with its own terms and permissions. If you do not install it, the configuration file alone does not run the DCO check.

## Verify enforcement

After creating both rulesets, confirm they show **Active** and target `main`. Test from a non-admin account or ask a teammate to verify:

1. A direct push to `main` is rejected.
2. A PR missing either required status check cannot be merged.
3. A PR without an approval cannot be merged.
4. A normal contributor cannot merge a PR or update `main`.
5. The repository owner can merge a compliant PR but cannot bypass the quality gates.

You reported that both branch rulesets have been created and put into enforcement. Verify the direct-push and required-check behavior above, then add the DCO status check to the existing `main-quality-gates` ruleset after installing the App.
