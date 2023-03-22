# Stage 1: Build the application
FROM maven:3.8.3-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
RUN mvn dependency:go-offline
COPY src/ /app/src/
RUN mvn package -DskipTests

# Stage 2: Package the application in a lightweight runtime environment
FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/target/*.jar spring-boot-training-jar.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/spring-boot-training-jar.jar"]
