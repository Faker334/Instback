FROM adoptopenjdk/openjdk11:alpine-jre
EXPOSE 8080
COPY target/inst.jar app.jar

CMD ["java", "-jar", "app.jar"]

