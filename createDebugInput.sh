#!/bin/bash
set -e

# --------------------------------------------------------------------------------
# Will use protoc on the test-proto file and store the CodeGenerationRequest in text-form in ./generated/code_generator_request.pb.bin
# This allows you to debug the plugin by adding "debugInput" as a program argument (see main.cpp)
# --------------------------------------------------------------------------------

mkdir -p build
cd build || exit
cmake ..
make
cd ..

go build -o ./build/protoc-gen-debug ./protoc-gen-debug/main.go
protoc \
  --plugin=protoc-gen-debug=./build/protoc-gen-debug \
  --debug_out=./generated \
  --proto_path=./java/src/main/proto \
  --proto_path=./java/src/test/proto \
  ./java/src/test/proto/*.proto
