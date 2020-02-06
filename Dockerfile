FROM openjdk:8-jdk-alpine
VOLUME /tmp
EXPOSE 8084
RUN mkdir -p /app/
RUN mkdir -p /app/logs/
ADD target/2-1.0-SNAPSHOT.jar /app/app.jar
ENV DATABASE_URL=jdbc:postgresql://postgres:5432/student_handbook
ENV DATABASE_USERNAME=vlad
ENV DATABASE_PASSWORD=bar
ENTRYPOINT ["java","-jar","-Dspring.datasource.url=${DATABASE_URL}","-Dspring.datasource.username=${DATABASE_USERNAME}", "-Dspring.datasource.password=${DATABASE_PASSWORD}","-Djava.security.egd=file:/dev/./urandom","/app/app.jar"]