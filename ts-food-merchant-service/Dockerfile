FROM eclipse-temurin:8-jre-alpine

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-food-merchant-service-1.0.jar /app/
CMD ["java", "-Xmx200m",  "-jar", "/app/ts-food-merchant-service-1.0.jar"]

EXPOSE 18855
