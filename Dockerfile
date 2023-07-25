FROM gradle:8-jdk17-alpine as build

COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN ./gradlew build --no-daemon

FROM amazoncorretto:17-alpine-jdk
COPY --from=build /home/gradle/src/build/libs/homework.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]