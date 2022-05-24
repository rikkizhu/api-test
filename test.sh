#!/bin/bash

set -e

## Staging 配置
export BASE_URL=http://dev-platform.staging.xxx
export PAGES_TEST_DOMAIN=test.stagpages.zhuruiqi.com
export PHPMYADMIN_URL=phpmyadmin.pages.xxxprod.net
export DATABASE_HOST=127.0.0.1
export SITE_DATABASE_PORT=9904
export SHARDING_GROUP=xxx-testing
export PAGES_SITE_DOMAIN=pages.xxxprod.net
export PLUGIN_SCORE_ID=48
export CLOUDSTUDIO_PLUGIN_MATERIAL_ID=277

## Production 配置
#export BASE_URL=https://studio.dev.xxx.com
#export PAGES_TEST_DOMAIN=test.prodpages.zhuruiqi.com
#export PHPMYADMIN_URL=phpmyadmin.xxx.io
#export DATABASE_HOST=mysql.xxx.io
#export SITE_DATABASE_PORT=3306
#export SHARDING_GROUP=xxx-workspace
#export PAGES_SITE_DOMAIN=xxx.io
#export PLUGIN_SCORE_ID=465
#export CLOUDSTUDIO_PLUGIN_MATERIAL_ID=21

## Testing 配置
#export BASE_URL=http://dev-platform.testing.xxx
#export PAGES_TEST_DOMAIN=
#export PHPMYADMIN_URL=phpmyadmin.ocd.testing
#export DATABASE_HOST=127.0.0.1
#export SITE_DATABASE_PORT=9904
#export SHARDING_GROUP=xxx-dev-platform-testing
#export PAGES_SITE_DOMAIN=ocd.testing
#export PLUGIN_SCORE_ID=8
#export CLOUDSTUDIO_PLUGIN_MATERIAL_ID=47

APP_PATH="$(cd "$( dirname "${BASH_SOURCE[0]}" )" >/dev/null && pwd)"
APP_NAME="$(basename "$APP_PATH")"
ROOT_PATH="${APP_PATH}/../../"
MAVEN_CACHE_PATH="${ROOT_PATH}/cache/m2"

if [ ! -d ${MAVEN_CACHE_PATH} ]; then
    mkdir -p ${MAVEN_CACHE_PATH}
fi

docker run --rm \
    -w /work \
    -v ${ROOT_PATH}:/work \
    -v ${MAVEN_CACHE_PATH}:/root/.m2 \
    -v ${ROOT_PATH}/config/settings.xml:/usr/share/maven/conf/settings.xml \
    -v /var/run/docker.sock:/var/run/docker.sock \
    -e MAVEN_OPTS='-XX:+TieredCompilation -XX:TieredStopAtLevel=1' \
    -e BASE_URL=${BASE_URL} \
    -e PAGES_TEST_DOMAIN=${PAGES_TEST_DOMAIN} \
    -e PHPMYADMIN_URL=${PHPMYADMIN_URL} \
    -e DATABASE_HOST=${DATABASE_HOST} \
    -e SITE_DATABASE_PORT=${SITE_DATABASE_PORT} \
    -e SHARDING_GROUP=${SHARDING_GROUP} \
    -e PAGES_SITE_DOMAIN=${PAGES_SITE_DOMAIN} \
    -e PLUGIN_SCORE_ID=${PLUGIN_SCORE_ID} \
    -e CLOUDSTUDIO_PLUGIN_MATERIAL_ID=${CLOUDSTUDIO_PLUGIN_MATERIAL_ID} \
    maven:3.5.4-jdk-8-alpine \
    sh -c "cd app/${APP_NAME} && mvn -T 1C test && cd - && chown -R $(id -u):$(id -g) cache app/${APP_NAME}/target"
#   sh -c "cd app/${APP_NAME} && mvn -T 1C test -Dtest=net.xxx.xxx.accessurl.* && cd - && chown -R $(id -u):$(id -g) cache app/${APP_NAME}/target"
