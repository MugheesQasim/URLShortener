FROM openjdk:21-jdk
WORKDIR /app
COPY target/urlshortener-0.0.1.jar app.jar
ENV PORT=8080
EXPOSE ${PORT}
ENTRYPOINT ["java", "-jar", "app.jar"]
