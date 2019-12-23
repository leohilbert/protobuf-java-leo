#!/bin/bash -e

docker build -t leohilbert/protoc-leo .

docker run --rm -v $(pwd):/wd -w /wd leohilbert/protoc-leo:latest \
  --plugin=protoc-gen-java-leo=/usr/local/bin/protoc-gen-java-leo \
  --proto_path=./java/src/main/proto \
  --proto_path=./java/src/test/proto \
  --java-leo_out=./generated \
 ./java/src/test/proto/addressbook.proto