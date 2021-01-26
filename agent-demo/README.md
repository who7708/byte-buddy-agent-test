# spring-boot-demo

#### 说明

1. 集成 arthas
2. 集成 jq
3. 集成 actuator

   base.Dockerfile

### 配置

测试用的, 开启 actuator

```properties
server.port=7001

management.server.port=7002
management.endpoint.health.show-details=always
management.endpoints.web.exposure.include=*
```

### 测试接口

1. GET /
2. GET /?name=cheney
3. GET /random

### 更新

#### 2021/01/25

* agent

```
java -agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005 -javaagent:agent.jar -Dserver.port=7001 -jar target/spring-boot-demo.jar
```

#### 2021/01/21

* 新增 打包/启动脚本
* 更新 `maven` build
* 增加 `maven-antrun-plugin` 插件
* 增加 `Dockerfile` 配置文件
* 增加打包 `shell`
* 更新 spring-boot 版本 2.4.2
* 新增 management
* 删除无用文件
* 更新 Dockerfile

