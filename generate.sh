#!/bin/bash
set -e

# --------------------------------------------------------------------------------
# Compiles the project and generates the javaclasses for my test-proto file into ./generated
# --------------------------------------------------------------------------------

mkdir -p build

protoc --proto_path=java/src/test/proto \
  --cpp_out src/leo/proto \
  java/src/test/proto/leo_options.proto

cd build || exit
cmake ..
make
cd ..

rm -rf generated/com
mkdir -p generated/com
protoc \
  --plugin=protoc-gen-java-leo=build/protoc-gen-java-leo \
  --proto_path=./java/src/main/proto \
  --proto_path=./java/src/test/proto \
  --java-leo_out=./generated \
  ./java/src/test/proto/addressbook.proto
