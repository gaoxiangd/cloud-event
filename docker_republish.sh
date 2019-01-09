#!/bin/sh

#set -e

basepath=$(cd `dirname $0`; pwd)
cd ${basepath}

IN_PROFILE=$1
IN_PORT=$2

source ./scripts/config.sh

deploy_in_docker true ${IN_PROFILE} ${IN_PORT}

sleep 10s

tail -f ${BIND_DIR}/logs/${APPLICATION_NAME}.log

