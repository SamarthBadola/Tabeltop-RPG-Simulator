# Application

Spring Boot 4.1.1 application using Temurin Java 25, Maven Wrapper 3.10.0, Thymeleaf, Spring Security, PostgreSQL, and Flyway.

From the repository root, initialise the local environment and start the source-built stack using the instructions in the root README. For application-only development with a local PostgreSQL database available through the configured environment variables:

\`\`\`bash
cd app
./mvnw -B -ntp spotless:check test
./mvnw -B -ntp verify -Pintegration
\`\`\`

The \`integration\` profile uses Testcontainers and therefore requires a working Docker daemon. The first-account registration transaction locks a singleton initialization row; only the first user is created as the instance administrator. Additional registrations remain closed until the invitation flow is implemented.

Keep any future rules engine under a pure-JDK \`engine\` package. It must not import Spring, JPA, HTTP, or filesystem I/O.
