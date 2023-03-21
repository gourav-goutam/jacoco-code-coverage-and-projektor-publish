FROM openjdk:11

COPY projektor-server.jar /app.jar

ENV DB_USERNAME=admin
ENV DB_PASSWORD=admin
ENV DB_URL=jdbc:postgresql://postgres:5432/projektordb

ENTRYPOINT ["java", "-jar", "/app.jar"]
