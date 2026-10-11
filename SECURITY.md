# Security Policy

This is a course project. Do not put credentials, access tokens, real user data, or private campaign exports in issues or pull requests.

For a suspected vulnerability, report it privately to the repository maintainers rather than opening a public issue containing an exploit or sensitive data. Include a concise impact statement and reproducible steps where safe.

Security requirements for implementation are documented in `docs/architecture.md`, including default-authenticated routes, data-layer campaign authorization, CSRF, safe uploads/imports, server-side audience filtering, and secrets supplied only through environment variables or GitHub's secret store.
