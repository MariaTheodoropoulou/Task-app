FROM openjdk:17-jdk-alpine
VOLUME /tmp

RUN apk add --no-cache bash

COPY wait-for-it.sh /usr/local/bin/wait-for-it.sh
RUN chmod +x /usr/local/bin/wait-for-it.sh

COPY build/libs/task-app-0.0.1-SNAPSHOT.jar app.jar

LABEL authors="LENOVO"

ENTRYPOINT ["/usr/local/bin/wait-for-it.sh", "mysql", "3306", "--", "java", "-jar", "app.jar"]
