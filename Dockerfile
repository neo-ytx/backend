FROM openjdk:11
ADD target/backend-0.0.1-SNAPSHOT.jar /app.jar
#ADD wait-for-it.sh /wait-for-it.sh
EXPOSE 8080
CMD ["java", "-jar","/app.jar"]
