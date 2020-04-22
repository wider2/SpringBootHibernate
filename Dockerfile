FROM java:8-jdk-alpine
COPY ./target/task-0.0.1-SNAPSHOT.jar /publish/
WORKDIR /publish
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "task-0.0.1-SNAPSHOT.jar"]
