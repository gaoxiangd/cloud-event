#!/bin/sh

DEST_DIR="dest"

PORT=23550
APPLICATION_NAME=cloudevent
SERVICE_NAME=${APPLICATION_NAME}-server
EXECUTE_JAR=${SERVICE_NAME}.jar

CONFIG_USERNAME="welian"
CONFIG_PASSWORD="welian"
CONFIG_LABEL="dev"
CONFIG_PROFILE="dev"
CONFIG_URI="http://192.168.1.253:10000"


BIND_DIR_ROOT=/data/containers


#get bind_dir path
function bind_dir_path(){
    BIND_DIR=${BIND_DIR_ROOT}/${APPLICATION_NAME}-${CONFIG_PROFILE}-${PORT}
}

#get container name
function container_name(){
    CONTAINER_NAME=${APPLICATION_NAME}-${CONFIG_PROFILE}-${PORT}
}

#get image name
function image_name(){
    IMAGE_NAME=${APPLICATION_NAME}
}

#build docker image
function docker_build(){
    image_name
    docker build -t ${IMAGE_NAME} .
}

#remove all unuse images
function docker_rm_images(){
    docker images |grep none | grep -v grep
    if [ $? -ne 0 ]
    then
    echo "no images need remove"
    else
    echo "remove images"
    docker images -a |awk '{print $3}' | xargs docker rmi -f
    fi
}

#remove all never use containers
function docker_rm_containers(){
    container_name
    echo "${CONTAINER_NAME}|${PORT}"
    docker ps -a | grep -E "${CONTAINER_NAME}|${PORT}" | grep -v grep
    if [ $? -ne 0 ]
    then
    echo "no container need remove"
    else
    echo "remove containers"
    docker ps -a |grep -E "${CONTAINER_NAME}|${PORT}" |awk '{print $1}' | xargs docker stop
    docker ps -a |grep -E "${CONTAINER_NAME}|${PORT}" |awk '{print $1}' | xargs docker rm -f
    fi
}

# run a docker container with image
function docker_run(){
    container_name
    bind_dir_path
    image_name
    docker run -ti -d \
    --net host \
    --name ${CONTAINER_NAME} \
    --restart always \
    -v ${BIND_DIR}/logs:/data/logs \
    --env PORT=${PORT} \
    --env CONFIG_URI=${CONFIG_URI} \
    --env CONFIG_USERNAME=${CONFIG_USERNAME} \
    --env CONFIG_PASSWORD=${CONFIG_PASSWORD} \
    --env CONFIG_LABEL=${CONFIG_LABEL} \
    --env CONFIG_PROFILE=${CONFIG_PROFILE} \
    --env APPLICATION_NAME=${APPLICATION_NAME} \
    --env EXECUTE_JAR=${EXECUTE_JAR} \
    ${IMAGE_NAME}
}

#mvn package
function mvn_package(){
    WITH_TEST=$1

    mvn clean

    echo "clean compile clean package"
    if test -z "${WITH_TEST}"
    then
        mvn -U clean compile package -Dmaven.test.skip=true
    else
        mvn -U clean compile package
    fi

    #remove space
    DEST_DIR=`echo ${DEST_DIR} | sed s/[[:space:]]//g`

    if test -z "${DEST_DIR}"
    then
        echo "Error DEST_DIR empty"
        exit 1
    else
        rm -fr ${DEST_DIR}/*
        mkdir -pv ${DEST_DIR}
        cp -r scripts/* ${DEST_DIR}/
    fi


    ORG_PATH=${SERVICE_NAME}/target/${APPLICATION_NAME}-server.jar

    # 这里的-f参数判断 ${ORG_PATH} 是否存在
    if [ -f "${ORG_PATH}" ];
    then
        echo "copy ${ORG_PATH} "
        cp ${ORG_PATH} ${DEST_DIR}/${EXECUTE_JAR}
        echo "success"
    else
        echo "打包失败"
        exit 1
    fi
}

#start service
function start(){
    nohup java -Dfile.encoding=UTF-8 -Xmx512m -Xms128m -Xss256k \
    -jar ${EXECUTE_JAR}.jar \
    --PORT=${PORT} \
    --APPLICATION_NAME=${APPLICATION_NAME} \
    --CONFIG_URI=${CONFIG_URI} \
    --CONFIG_USERNAME=${CONFIG_USERNAME} \
    --CONFIG_PASSWORD=${CONFIG_PASSWORD} \
    --CONFIG_LABEL=${CONFIG_LABEL} \
    --CONFIG_PROFILE=${CONFIG_PROFILE} &
}

#shutdown service
function shutdown(){
    echo "stop ${APPLICATION_NAME}"
    #notify java process shutdown
    ps -ef | grep -E "${APPLICATION_NAME}" | grep -v grep | awk '{print $2}' | xargs kill -15
    sleep 10s
    # focus kill java process
    ps -ef | grep -E "${APPLICATION_NAME}" | grep -v grep | awk '{print $2}' | xargs kill -9
}

# docker 部署服务
function deploy_in_docker(){
    IN_REMOVE_CONTAINERS=$1
    IN_PROFILE=$2
    IN_PORT=$3
    # 更新代码
    git pull
    # 打包并拷贝文件
    mvn_package

    # 设置profile
    if test -z "${IN_PROFILE}"
    then
        echo "PROFILE not set"
    else
        CONFIG_PROFILE=${IN_PROFILE}
    fi
    # 设置端口
    if test -z "${IN_PORT}"
    then
        echo "PORT not set"
    else
        PORT=${IN_PORT}
    fi

    cd ${DEST_DIR}
    # 构建镜像
    docker_build

    # 关闭旧容器
    if [ "${IN_REMOVE_CONTAINERS}"s  = "true"s ] ; then
        docker_rm_containers
    else
        echo "do not remove containers"
    fi

    # 启动新容器
    docker_run
    # 删除旧镜像
    docker_rm_images

}