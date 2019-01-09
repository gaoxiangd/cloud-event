#!/bin/sh
#set -e

basepath=$(cd `dirname $0`; pwd)
cd ${basepath}

APPLICATION_NAME=cloudevent
SERVICE_NAME=${APPLICATION_NAME}-server
#是否覆盖原来client文件
COVER_STATUS=true
#扫包路径
SCAN_PACKAGE=com.welian.controller
function generate_client(){
   mvn clean compile -U
   #如果项目名字就是server直接用server
   cd  ${SERVICE_NAME}
   mvn exec:java -Dexec.mainClass="org.sean.framework.util.Factory" -Dexec.args=${SCAN_PACKAGE}" "${COVER_STATUS}

}

generate_client