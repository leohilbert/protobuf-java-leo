set -e
mkdir -p build

protoc --proto_path=java/src/test/proto \
  --cpp_out src/javaleo/proto \
  java/src/test/proto/options.proto

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