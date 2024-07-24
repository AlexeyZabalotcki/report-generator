FROM maven:3.8.3-openjdk-17 AS build
ENV APP_HOME=/app/
WORKDIR $APP_HOME
COPY pom.xml $APP_HOME
COPY src $APP_HOME/src/
RUN mvn package -DskipTests
RUN ls -l $APP_HOME/target/ # Debugging: List the contents of the target directory

FROM openjdk:17-oracle
ENV APP_HOME=/app/
ENV ARTIFACT_NAME=demo-0.0.1-SNAPSHOT.jar
ARG JAR_FILE=$APP_HOME/target/$ARTIFACT_NAME
COPY --from=build $JAR_FILE /opt/demo/app.jar
RUN ls -l /opt/demo/ # Debugging: Confirm the JAR file is present and check permissions
ENTRYPOINT ["java", "-jar", "/opt/demo/app.jar"]
