# Use an official OpenJDK runtime as a parent image
FROM openjdk:22-jdk

# Set the working directory inside the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container
# Make sure your JAR is named correctly. You may use a build system like Maven or Gradle to generate this.
COPY target/mc-forum-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the port that your Spring Boot application listens on
EXPOSE 8080

# Run the JAR file
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
