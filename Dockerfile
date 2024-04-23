FROM openjdk:17-jdk-slim
COPY target/PalmoilMnger-0.0.1.jar run.jar

EXPOSE 6061
ENTRYPOINT ["java","-jar","./run.jar"]