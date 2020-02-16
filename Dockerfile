FROM openjdk:8-jre-alpine

MAINTAINER ahmed.saleh.mahgoub@gmail.com

COPY target/* /tweet/

CMD ["java",  "-cp", "/tweet/bieber-tweets-1.0.0-SNAPSHOT.jar", "ApplicationMain"]