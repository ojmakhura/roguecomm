# Dockerfile for Native Image
FROM eclipse-temurin:21-jre-jammy

RUN mkdir -p /app
WORKDIR /app

ARG WAR_FILE=webservice/target/bitri-comm-webservice*.jar
COPY ${WAR_FILE} bitri-comm-webservice.jar
COPY comm_start.sh comm_start.sh

ENTRYPOINT ["sh", "/app/comm_start.sh"]