# Etapa 1: Compilación
FROM openjdk:21-jdk-slim

COPY pom.xml .
COPY src/ /app/src/

COPY src/main/resources/application.yml application.yml

MAINTAINER baeldung.com
COPY target/classes/.env .env
COPY target/bankinc-0.0.2.jar bankinc-0.0.2.jar
ENTRYPOINT ["java","-jar","/bankinc-0.0.2.jar"]