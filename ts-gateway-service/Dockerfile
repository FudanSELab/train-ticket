FROM eclipse-temurin:8-jre-alpine

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-gateway-service-1.0.jar /app/
CMD ["java", "-Xmx1024m",  "-jar", "/app/ts-gateway-service-1.0.jar"]

EXPOSE 18888 
