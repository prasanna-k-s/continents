FROM adoptopenjdk/openjdk11
VOLUME /redis-service
ARG JAR_FILE=build/libs/continents-0.0.1-SNAPSHOT.jar
ADD ${JAR_FILE} continents-service.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/continents-service.jar"]