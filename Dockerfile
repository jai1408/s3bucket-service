FROM openjdk:8
ARG JAR_FILE=target/s3bucket-service.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080