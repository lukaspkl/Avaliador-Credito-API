
FROM maven:3.8.1-adoptopenjdk-15 as build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:15
WORKDIR /app
COPY --from=build ./app/target/*.jar ./app.jar

ARG EUREKA_SERVER=localhost


ENTRYPOINT java -jar app.jar

