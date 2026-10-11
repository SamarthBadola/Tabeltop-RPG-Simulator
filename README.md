# Tabletop RPG Simulator

A self-hosted, browser-based tool for Dungeon Masters: character management and encounter running, backed by a pure Java rules engine and PostgreSQL. The optional pathfinding service is a separate C++17 process. This is not a virtual tabletop.

## Project baseline

- Java 21, Spring Boot, Maven Wrapper
- PostgreSQL 16 with Flyway migrations
- Thymeleaf + self-hosted HTMX
- C++17 pathfinder service (optional at runtime; Java fallback remains available)
- Docker Compose for local/LAN use
- GitHub Actions for CI; GHCR for versioned container images
- D&D 5e SRD 5.1 (2014 rules) only, with the required CC-BY-4.0 attribution

See [`docs/architecture.md`](docs/architecture.md) for the architecture and [`docs/Tabletop RPG Simulator AI Build Brief.md`](docs/Tabletop%20RPG%20Simulator%20AI%20Build%20Brief.md) for implementation constraints and the build order.

## Current status

Repository bootstrap only. Application and pathfinder source code have not been generated yet. The first implementation milestone is the walking skeleton: Spring Boot health endpoints, PostgreSQL + Flyway, authentication baseline, Docker Compose, and full CI checks.

## Non-negotiable engineering rules

- Implement D&D mechanics only from the project ruleset specification; do not implement mechanics from memory.
- Keep `engine` JDK-only and free of I/O, Spring, JPA and HTTP dependencies.
- Do not commit secrets, `.env` files, local build output, or user data.
- Use additive Flyway migrations; never edit an applied migration.
- Test engine behavior first; never weaken or rewrite tests just to make them pass.
- Use short-lived branches, Conventional Commits, pull requests, and squash merges.

## Local setup

The one-command setup will be documented when the Step 1 walking skeleton exists. Do not invent `.env` values or run an incomplete Compose stack yet.
