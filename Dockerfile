FROM maven:3.8.4-openjdk-17 as maven-builder
COPY src /app/src
COPY pom.xml /app

RUN mvn -f /app/pom.xml clean install -DskipTests
FROM openjdk:17-alpine

COPY --from=maven-builder app/target/*.jar app.jar
WORKDIR /app

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]