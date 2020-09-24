FROM openjdk

EXPOSE 9292 27017

ADD target/nano-0.0.1-SNAPSHOT.jar nano.jar

ENTRYPOINT ["java", "-jar", "nano.jar"]