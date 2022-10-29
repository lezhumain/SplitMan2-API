FROM alpine

RUN apk update
RUN apk add vim
RUN apk add openjdk17
RUN apk add maven

WORKDIR /app
COPY ./target/app.jar /app/

#RUN cd /app && mvn compile
#
## CMD [“echo”, “hi”…]
##CMD ["mvn", "-v"]
#CMD ["mvn", "spring-boot:run", "-f", "pom.xml"]

CMD ["java", "-jar", "-f", "/app/app.jar"]

EXPOSE 8080/tcp