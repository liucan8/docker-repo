FROM daocloud.io/java:latest
VOLUME /tmp
ADD idea1-1.0-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
