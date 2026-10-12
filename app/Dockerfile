FROM eclipse-temurin:26.0.2.1_1-jdk-noble AS build
WORKDIR /src
COPY .mvn .mvn
COPY mvnw pom.xml ./
RUN chmod +x ./mvnw && ./mvnw -B -ntp dependency:go-offline
COPY src src
RUN ./mvnw -B -ntp -DskipTests package

FROM eclipse-temurin:26.0.2.1_1-jre-noble
RUN useradd --system --uid 10001 app
WORKDIR /app
COPY --from=build --chown=app:app /src/target/tabletop-rpg-simulator.jar /app/app.jar
USER app
EXPOSE 8080
ENTRYPOINT ["java", "-XX:MaxRAMPercentage=75", "-jar", "/app/app.jar"]
