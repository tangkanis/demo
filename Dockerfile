## Use official base image of Java Runtime
#FROM openjdk:17-alpine
#
## Copy jar file to container
#COPY target/demo-0.0.1-SNAPSHOT.jar app.jar
#
## Set port
#EXPOSE 8080
#
## Run the JAR file
#ENTRYPOINT ["java","-jar","/app.jar"]

FROM maven:3.8.4-openjdk-17 as maven-builder
COPY src /app/src
COPY pom.xml /app

RUN mvn -f /app/pom.xml clean install -DskipTests
FROM openjdk:17-alpine

COPY --from=maven-builder app/target/*.jar app.jar
WORKDIR /app

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]