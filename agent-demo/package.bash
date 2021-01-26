#!/usr/bin/env bash

# maven 打包
mvn clean package -Dmaven.test.skip=true -T 10 -e

TIME=`date "+%Y%m%d%H%M%S"`

echo "DOCKER TAG IS ${TIME}"

# 打镜像
docker build -t spring-boot-demo:"${TIME}" .

# 启动
docker run -it --rm --name spring-boot-demo -p 7001:7001 -p 7002:7002 spring-boot-demo:"${TIME}" /bin/sh
