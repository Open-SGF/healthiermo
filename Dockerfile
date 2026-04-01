# Build stage
FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copy pom.xml and download dependencies
COPY pom.xml .
RUN mvn dependency:go-offline

# Copy source code and build the application
COPY src ./src
RUN mvn clean package -DskipTests

# Runtime stage
FROM eclipse-temurin:21-jre
WORKDIR /app

# Default runtime data root
ENV APP_PUBLIC_DATA_ROOT=/data

# Create directory for audio files
RUN mkdir -p /data/audio-files

# Copy the built JAR from build stage
COPY --from=build /app/target/*.jar app.jar

# Expose port 80
EXPOSE 80

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
