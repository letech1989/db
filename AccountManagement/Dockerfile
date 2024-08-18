# Use the official OpenJDK image from the Docker Hub as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file from the build artifact location to the container
COPY target/myapp.jar /app/myapp.jar

# Expose the port the application runs on
EXPOSE 8080

# Define the command to run the JAR file
ENTRYPOINT ["java", "-jar", "/app/myapp.jar"]
