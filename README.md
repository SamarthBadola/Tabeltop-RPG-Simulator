# Tabletop RPG Simulator

A self-hosted, browser-based tool for Dungeon Masters: character management and encounter running, backed by a pure Java rules engine and PostgreSQL. The optional pathfinding service is a separate C++17 process. This is not a virtual tabletop.

## Project baseline

- Java 25 (Temurin LTS)
- Spring Boot 4.1.1
- Maven Wrapper targeting Maven 3.10.0
- PostgreSQL 16.15 with Flyway migrations
- Thymeleaf + self-hosted HTMX
- C++17 pathfinder service (optional at runtime; Java fallback remains available)
- Docker Compose for local/LAN use
- GitHub Actions for CI; GHCR for versioned container images
- D&D 5e SRD 5.1 (2014 rules) only, with the required CC-BY-4.0 attribution

## Maven and Java identifiers

- Maven `groupId`: `com.github.sbadolankaur`
- Maven `artifactId`: `tabletop-rpg-simulator`
- Java root package: `com.github.sbadolankaur.rpg`

## Licensing and third-party attribution

Project-owned code and documentation are licensed under the Apache License, Version 2.0. See [LICENSE](LICENSE). This license does not replace the separate license terms that apply to third-party components or SRD material.

### SRD 5.1 attribution

This work includes material taken from the System Reference Document 5.1 (“SRD 5.1”) by Wizards of the Coast LLC and available at https://dnd.wizards.com/resources/systems-reference-document. The SRD 5.1 is licensed under the Creative Commons Attribution 4.0 International License available at https://creativecommons.org/licenses/by/4.0/legalcode.

See [`docs/architecture.md`](docs/architecture.md) for the architecture and [`docs/Tabletop RPG Simulator AI Build Brief.md`](docs/Tabletop%20RPG%20Simulator%20AI%20Build%20Brief.md) for implementation constraints and the build order. Exact toolchain choices and open decisions are recorded in [`docs/DECISION-LOG.md`](docs/DECISION-LOG.md).

## Current status

Repository bootstrap complete. The bootstrap CI jobs have passed on the pull request and on `main`. Application and pathfinder source code have not been generated yet. The next implementation milestone is the walking skeleton: Spring Boot health endpoints, PostgreSQL + Flyway, authentication baseline, Docker Compose, and full CI checks.

## Non-negotiable engineering rules

- Implement D&D mechanics only from the project ruleset specification; do not implement mechanics from memory.
- Keep `engine` JDK-only and free of I/O, Spring, JPA and HTTP dependencies.
- Do not commit secrets, `.env` files, local build output, or user data.
- Use additive Flyway migrations; never edit an applied migration.
- Test engine behavior first; never weaken or rewrite tests just to make them pass.
- Use short-lived branches, Conventional Commits, pull requests, and squash merges.

## Local setup

The one-command setup will be documented when the Step 1 walking skeleton exists. Do not invent `.env` values or run an incomplete Compose stack yet.
