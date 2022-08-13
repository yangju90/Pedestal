FROM base/openjdk:11-jdk-alpine
VOLUME /tmp
ENV TZ=America/Los_Angeles
COPY docker/ ./
RUN sed -i 's/\r//' docker-entrypoint.sh && chmod +x docker-entrypoint.sh
COPY target/*.jar pda.jar
ENTRYPOINT ["sh","docker-entrypoint.sh"]
