FROM openjdk:17-oracle
LABEL authors="vishesh"
COPY target/Leave-portal-0.0.1-SNAPSHOT.jar /Leave-portal-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","./Leave-portal-0.0.1-SNAPSHOT.jar"]