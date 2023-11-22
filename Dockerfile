# For Java 8, try this
# FROM openjdk:8-jdk-alpine

# For Java 11, try this
FROM openjdk:17-jdk-slim

# Refer to Maven build -> finalName
#ARG JAR_FILE=target/spring-boot-web.jar

# cd /opt/app
WORKDIR /opt/app

# cp target/spring-boot-web.jar /opt/app/app.jar
COPY target/*.jar app.jar

# java -jar /opt/app/app.jar
ENTRYPOINT ["java","-jar","app.jar"]
