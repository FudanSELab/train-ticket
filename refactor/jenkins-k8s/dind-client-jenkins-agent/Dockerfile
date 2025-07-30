FROM jenkins/jnlp-agent-docker
USER root

COPY entrypoint.sh /entrypoint.sh
RUN chown jenkins:jenkins /entrypoint.sh
RUN chmod +x /entrypoint.sh

USER jenkins
ENTRYPOINT "/entrypoint.sh"