FROM java
ADD ./www-0.0.1-SNAPSHOT.jar /my.jar
ENTRYPOINT [ "java" , "-jar" , "/my.jar"]
