FROM eclipse-temurin:8-jre-alpine

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-user-notification-service-1.0.jar /app/
CMD ["java", "-Xmx200m",  "-jar", "/app/ts-user-notification-service-1.0.jar"]

EXPOSE 17853