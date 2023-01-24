FROM openjdk:20-ea-17

ADD target/*.jar /app/app.jar

RUN chmod 777 /app/app.jar

WORKDIR /app

CMD ["java", "-jar", "app.jar"]