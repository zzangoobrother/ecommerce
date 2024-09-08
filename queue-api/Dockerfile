FROM openjdk:21-ea-11-jdk-slim

COPY build/libs/queue-api-0.0.1.jar app.jar
ENV TZ=Asia/Seoul
ENTRYPOINT ["java", "-Dspring.profiles.active=${USE_PROFILE}", "-jar", "app.jar"]
