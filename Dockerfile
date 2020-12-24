FROM maven:3.6.1-jdk-11-slim AS build
COPY . /usr/odxetest
WORKDIR /usr/odxetest
RUN mvn clean package -DskipTests

EXPOSE 8080
CMD ["java", "-jar", "target/odxetest-0.0.1-SNAPSHOT.jar"]