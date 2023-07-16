FROM eclipse-temurin:17-jdk-alpine
WORKDIR /opt/app
COPY target/*.jar $WORKDIR
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=prod", "cart-service.jar"]