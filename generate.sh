set -e
mkdir -p build
cd build || exit
cmake ..
make
cd ..

rm -rf generated
mkdir -p generated
protoc \
--plugin=protoc-gen-java-leo=build/protoc-gen-java-leo \
--java-leo_out=./generated ./java/src/main/proto/addressbook.proto

#diff -arq tests/actual tests/expected
#exit "$?"