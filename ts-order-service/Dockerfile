FROM eclipse-temurin:11-jre-alpine

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-order-service-1.0.jar /app/
CMD ["java", "-Xmx200m",  "-jar", "/app/ts-order-service-1.0.jar"]
#CMD java $JAVA_OPTIONS -jar /app/ts-order-service-1.0.jar

EXPOSE 12031