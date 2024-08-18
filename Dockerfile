FROM amazoncorretto:21-alpine-jdk

COPY target/Foro-Hub-0.0.1-SNAPSHOT.jar app.jar
COPY target/Foro-Hub-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]