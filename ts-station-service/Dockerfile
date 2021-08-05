FROM java:8-jre

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-station-service-1.0.jar /app/
CMD ["java", "-Xmx200m",  "-jar", "/app/ts-station-service-1.0.jar"]

EXPOSE 12345