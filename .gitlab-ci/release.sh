#!/bin/sh
set -e

IMAGE_NAME=${DEV_HARBOR}/${HARBOR_PROJECT}/library/employee-service-api:${CI_COMMIT_TAG}
PRD_IMAGE_NAME=${PRD_HARBOR}/${HARBOR_PROJECT}/library/employee-service-api:${CI_COMMIT_TAG}

docker tag ${IMAGE_NAME} ${PRD_IMAGE_NAME}
docker login -u ${PRD_HARBOR_USERNAME} -p ${PRD_HARBOR_PASSWORD} ${PRD_HARBOR}
docker push ${PRD_IMAGE_NAME}
