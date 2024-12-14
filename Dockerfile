# Use official OpenJDK as base image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy project files
COPY target/app1.jar app1.jar

# Expose port
EXPOSE 9000

# Run the application
ENTRYPOINT ["java", "-jar", "app1.jar"]