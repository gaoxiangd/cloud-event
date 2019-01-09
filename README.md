创建完工程需要修改配置项
===
### 1. 修改yml配置文件
    修改对应端口号
    修改redis数据库index
### 2. 修改bootstrap.yml启动文件
    修改spring.application.name


修改版本号
===
    修改PROJECT_DIR/pom.xml
    <application.version>1.0-SNAPSHOT</application.version>
    1.0-SNAPSHOT --> xxx
    执行命令
    mvn versions:set

脚本说明
===
    Docker 发布正式环境:
        docker_offical_republish.sh
    Docker 发布开发环境最新版:
        docker_republish_latest.sh
    Docker 发布开发版:
        docker_republish.sh
    Docker 发布开发板(指定配置,指定端口,开启测试用例):
        docker_republish.sh snapshot 8080 true
    原生系统启动服务:
        restart.sh
    原生系统关闭服务:
        shutdown.sh