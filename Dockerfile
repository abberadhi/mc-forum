# Stage 1: Build the application using Maven
FROM maven:3.9.9-eclipse-temurin-22-jammy as build

# Set the working directory inside the container
WORKDIR /app

# Copy the entire project directory into the container
COPY . /app

# Build the application and create the JAR file
RUN mvn clean package -DskipTests

# Stage 2: Create the runtime image
FROM openjdk:22-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file from the build stage
COPY --from=build /app/target/mc-forum-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
