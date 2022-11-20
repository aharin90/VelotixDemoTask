FROM maven AS build
COPY src /src
COPY pom.xml .
RUN mvn -f /pom.xml clean install

FROM openjdk
COPY --from=build /target/VelotixDemo-0.0.1-SNAPSHOT.jar velotix.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","velotix.jar"]