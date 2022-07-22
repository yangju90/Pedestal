#!/bin/sh
set -e

# maven build
if [ "${SKIP_TEST}" = "true" ]; then
  mvn -B -DskipTests clean package install
else
  mvn -B clean compile org.jacoco:jacoco-maven-plugin:0.8.7:prepare-agent test org.jacoco:jacoco-maven-plugin:0.8.7:report package install
fi

# sonar check
mvn org.sonarsource.scanner.maven:sonar-maven-plugin:3.9.0.2155:sonar -Dsonar.host.url=${SONAR_URL} -Dsonar.login=${SONAR_LOGIN}

cd employee-service-api
# build docker image
IMAGE_NAME=${DEV_HARBOR}/${HARBOR_PROJECT}/library/employee-service-api:${CI_COMMIT_TAG}
OLD_IMAGE_ID=`docker images -q ${IMAGE_NAME}`
docker build --pull -t ${IMAGE_NAME} .
# push docker image to dev harbor
docker login -u ${DEV_HARBOR_USERNAME} -p ${DEV_HARBOR_PASSWORD} ${DEV_HARBOR}
docker push ${IMAGE_NAME}

# clean old docker image
if [ ! -z ${OLD_IMAGE_ID} ]; then
    if [ ! -z $(docker ps -aq -f ancestor=${OLD_IMAGE_ID}) ]; then
        docker rm -f `docker ps -aq -f ancestor=${OLD_IMAGE_ID}`
    fi
    docker rmi -f ${OLD_IMAGE_ID}
fi
docker image prune -f
