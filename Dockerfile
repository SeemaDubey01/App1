# Use official OpenJDK as base image
FROM openjdk:17

# Set working directory
WORKDIR /app

# Copy project files
COPY target/App1-1.0.jar App1-1.0.jar

# Expose port
EXPOSE 9000

# Run the application
ENTRYPOINT ["java", "-jar", "app1.jar"]