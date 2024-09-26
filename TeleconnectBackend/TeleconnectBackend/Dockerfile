# Base image
FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY . .
RUN chmod +x ./mvnw
RUN ./mvnw clean install
EXPOSE 8081
CMD ["java", "-jar", "target/TeleconnectBackend-0.0.1-SNAPSHOT.jar"]

