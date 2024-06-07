# 1. build
FROM gradle:7.5.1-jdk17 as build

WORKDIR /home/gradle/project

COPY . .

RUN gradle wrapper
RUN ./gradlew clean build -x test

# 2. Run the application
FROM eclipse-temurin:17-jdk-jammy

WORKDIR /opt/batch

COPY --from=build /home/gradle/project/build/libs/batch.jar .

CMD ["java", "-Dspring.profiles.active=prod", "-jar", "batch.jar"]