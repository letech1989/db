# Use the official OpenJDK image as a base
FROM 1.8.0_261

# Set the working directory
WORKDIR /app

# Copy the JAR file from the build context into the container
COPY target/UserAuthentication-1.0-SNAPSHOT.jar UserAuthentication-1.0.jar

# Specify the command to run the application
ENTRYPOINT ["java", "-jar", "myapp.jar"]

# Expose the port that your application will run on
EXPOSE 8080
