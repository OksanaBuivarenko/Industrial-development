FROM eclipse-temurin:17  AS build

WORKDIR /app

COPY ./database/gradlew .
COPY ./database/gradle/wrapper ./gradle/wrapper
COPY ./database/build.gradle.kts .
COPY ./database/settings.gradle.kts .
COPY ./database/src/main/ .

RUN chmod +x gradlew
RUN ./gradlew clean build --no-daemon

FROM eclipse-temurin:17

COPY --from=build /app/build/libs/database-0.0.1-SNAPSHOT-plain.jar /app/database.jar


EXPOSE 8086

CMD ["java", "-jar", "/app/database.jar"]