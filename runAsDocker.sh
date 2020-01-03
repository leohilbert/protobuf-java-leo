#!/bin/bash
set -e

IMAGE_NAME="leohilbert/protoc-gen-java-leo:latest"

docker build -t $IMAGE_NAME .
docker run --rm -v "$(pwd)":/wd -w /wd $IMAGE_NAME \
  protoc \
  --plugin=protoc-gen-java-leo=/usr/local/bin/protoc-gen-java-leo \
  --proto_path=./java/src/main/proto \
  --proto_path=./java/src/test/proto \
  --java-leo_out=./generated \
  ./java/src/test/proto/addressbook.proto
