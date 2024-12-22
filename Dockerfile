
FROM maven:3.8.6-openjdk-11 as builder
WORKDIR /app
COPY pom.xml .
RUN mvn clean install -DskipTests

COPY src ./src
RUN mvn package -DskipTests

FROM openjdk:11-jre-slim

WORKDIR /app

COPY --from=builder /app/target/*.jar /app/journeylens-3.4.0.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/app/journeylens-3.4.0.jar"]
