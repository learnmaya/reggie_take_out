# Set the base image
FROM openjdk:8-jdk-alpine

# Set the working directory
WORKDIR /app

# Copy the Spring Boot JAR file to the container
COPY reggie_take_out-1.0-SNAPSHOT.jar /app

# Expose the port on which your application runs
EXPOSE 8080

# Set the command to run the application
CMD ["java", "-jar", "reggie_take_out-1.0-SNAPSHOT.jar"]