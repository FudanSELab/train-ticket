FROM java:8-jre


ADD ./target/ts-resources.route-service-1.0.jar /app/
CMD ["java", "-Xmx200m", "-jar", "/app/ts-route-service-1.0.jar"]

EXPOSE 11178