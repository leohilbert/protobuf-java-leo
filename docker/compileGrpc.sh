#!/bin/sh
set -e

cd /tmp || exit 1
git clone -b v$1.x --recursive https://github.com/grpc/grpc-java.git
cd /tmp/grpc-java/compiler || exit 1
gradle java_pluginExecutable
