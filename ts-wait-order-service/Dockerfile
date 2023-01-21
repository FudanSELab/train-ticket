FROM openjdk:8-jre

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-wait-order-service-1.0.jar /app/
CMD ["java", "-Xmx200m",  "-jar", "/app/ts-wait-order-service-1.0.jar"]

EXPOSE 15678