#!/bin/bash
set -e

# --------------------------------------------------------------------------------
# Downloads and compiles the current supported protoc-release to place it into the "protoc"-folder.
# This should be used during compile to ensure the same protoc-binary&libraries is used.
# --------------------------------------------------------------------------------

latestProtocRelease=$(<fetch/protoc_commit.txt)

docker build -t leo/protoc:$latestProtocRelease --build-arg PROTOBUF_VERSION=$latestProtocRelease docker/protoc
containerName=$(docker create leo/protoc:$latestProtocRelease)

rm -rf protoc && mkdir protoc
docker cp $containerName:/out/usr/include protoc/include
docker cp $containerName:/out/usr/bin protoc/bin
docker cp $containerName:/out/usr/lib protoc/lib
docker rm $containerName
