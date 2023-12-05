FROM ubuntu:latest
LABEL authors="Gri"

ENTRYPOINT ["top", "-b"]
# Используйте официальный образ OpenJDK
FROM openjdk:11-jre-slim

# Установка рабочей директории внутри контейнера
WORKDIR /app

# Копирование JAR-файла внутрь контейнера
COPY target/inst-0.0.1-SNAPSHOT.jar app.jar

# Указание порта, который будет использоваться приложением
EXPOSE 8080

# Команда для запуска приложения при старте контейнера
CMD ["java", "-jar", "app.jar"]