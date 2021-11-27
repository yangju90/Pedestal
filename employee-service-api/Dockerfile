# docker build --pull -t java/logistics-demo:<tag> .
FROM harbor.newegg.org/base/openjdk:11-jdk-alpine
VOLUME /tmp
ENV TZ=America/Los_Angeles
COPY docker/ ./
RUN sed -i 's/\r//' docker-entrypoint.sh && chmod +x docker-entrypoint.sh
COPY target/*.jar pda-api.jar
ENTRYPOINT ["sh","docker-entrypoint.sh"]
