FROM openjdk:8-jdk-alpine

ARG MIRROR=true

ENV MAVEN_HOST=https://repo1.maven.org/maven2 \
    ALPINE_HOST=dl-cdn.alpinelinux.org \
    MIRROR_MAVEN_HOST=https://maven.aliyun.com/repository/public \
    MIRROR_ALPINE_HOST=mirrors.aliyun.com

# if use mirror change to aliyun mirror site
RUN if $MIRROR; then MAVEN_HOST=${MIRROR_MAVEN_HOST} ;ALPINE_HOST=${MIRROR_ALPINE_HOST} ; sed -i "s/dl-cdn.alpinelinux.org/${ALPINE_HOST}/g" /etc/apk/repositories ; fi && \
    # https://github.com/docker-library/openjdk/issues/76
    apk add --no-cache tini && \
    apk add --no-cache busybox-extras && \
    apk add --no-cache curl && \
    apk add --no-cache bash && \
    apk add --no-cache jq && \
    wget "https://arthas.aliyun.com/arthas-boot.jar" -qO /opt/arthas-boot.jar && \
    echo "java -jar /opt/arthas-boot.jar --repo-mirror aliyun" > /usr/local/bin/arthas && \
    chmod a+x /usr/local/bin/arthas

ENTRYPOINT ["/sbin/tini", "--"]
