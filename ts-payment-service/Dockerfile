FROM eclipse-temurin:8-jre-alpine

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-payment-service-1.0.jar /app/
CMD ["java", "-Xmx200m",  "-jar", "/app/ts-payment-service-1.0.jar"]

EXPOSE 19001