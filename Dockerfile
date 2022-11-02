FROM alpine

RUN apk update
RUN apk add vim
RUN apk add openjdk17
RUN apk add maven

WORKDIR /app
COPY ./target/app.jar /app/

CMD ["java", "-jar", "/app/app.jar"]

EXPOSE 8080/tcp
