FROM openjdk:21-jdk-bullseye

WORKDIR /app

RUN apt-get update && apt-get install -y maven

COPY ./server /app

RUN mvn package

COPY wait-for-it.sh ./wait-for-it.sh
RUN chmod +x ./wait-for-it.sh

# Wait for db to start
ENTRYPOINT ["./wait-for-it.sh", "db:5432", "--", "java", "-jar", "target/SimpleATM-ApacheThrift-1.0.jar"]