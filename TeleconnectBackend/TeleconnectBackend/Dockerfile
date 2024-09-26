# Base image
FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy all files into the Docker container
COPY . .

# Ensure the mvnw script is executable
RUN chmod +x ./mvnw


# Clean and build the application using Maven
RUN ./mvnw clean install

# Expose the application port (adjust if necessary)
EXPOSE 8081

# Run the Spring Boot application
CMD ["java", "-jar", "target/TeleconnectBackend-0.0.1-SNAPSHOT.jar"]
