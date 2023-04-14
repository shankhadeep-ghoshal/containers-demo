#syntax=docker/dockerfile:1.4
FROM gradle:8-jdk19-alpine as build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle clean bootJar

FROM eclipse-temurin:19-jre-alpine
EXPOSE 8080
RUN mkdir /app
COPY --from=build /home/gradle/src/build/libs/*.jar /app/post-app.jar
ENTRYPOINT ["java", "-jar","/app/post-app.jar"]
