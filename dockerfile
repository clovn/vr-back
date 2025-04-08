FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/vr-back-1.jar app.jar

ENV DB_URL=${DB_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}

# Команда для запуска приложения
ENTRYPOINT ["java", "-jar", "app.jar"]