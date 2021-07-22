FROM registry.cn-hangzhou.aliyuncs.com/h10g/skywalking-agent-rabbit-enhance:1.0

RUN /bin/cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo 'Asia/Shanghai' >/etc/timezone

ADD ./target/ts-notification-service-1.0.jar /app/
ENV SW_AGENT_NAME=ts-notification-service
CMD ["java", "-Xmx200m", "-javaagent:/skywalking/agent/skywalking-agent.jar", "-jar", "/app/ts-notification-service-1.0.jar"]

EXPOSE 17853