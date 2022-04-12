#!/bin/sh

location=${LOCATION}

option=
for arg in "$@"
do
    key=${arg%%=*}
    value=${arg#*=}
    case $key in
        --location)
            location=$value
        ;;
        *)
            option="$option $key=$value"
        ;;
    esac
done

if [ ! "${location}" ]; then
    echo "location not set!"
    exit 1
fi

echo "LOCATION: ${location}"
echo "TZ: ${TZ}"

HOST_UTL=""

# 根据不同服务器获取service config

if [ ${location} == 'gdev' ]; then
  HOST_UTL="apis-dev.xx.com"
elif [ ${location} == 'gqc' ]; then
  HOST_UTL="apis-gqc.xx.com"
elif [ ${location} == 'sandbox' ]; then
  HOST_UTL="sandboxapis.xx.com"
elif [[ ${location} == prd* ]]; then
  HOST_UTL="apis.xx.com"
else
  echo "location is error !"
fi

echo "https://${HOST_UTL}/keeper/v2/shipping-api/application/_rawdata"
wget -O ./application.yml  "https://${HOST_UTL}/keeper/v2/logistics-pda-api/application/_rawdata"


echo "JAVA_OPTS: ${JAVA_OPTS}"

//java ${JAVA_OPTS} -Dlogging.config="file:./logback.xml" -jar pda-api.jar${option}
java ${JAVA_OPTS} -Dlogging.config="file:./logback.xml" -Dspring.config.location="file:/tmp/application.yml" -DLOCATION=${location} -jar app.jar${option}

