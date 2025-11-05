LABEL authors="Pagooo"
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY build/libs/app-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
