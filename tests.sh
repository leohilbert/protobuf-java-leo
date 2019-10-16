set -e
mkdir -p build
cd build || exit
cmake ..
make
cd ..

rm -rf tests/actual
mkdir -p tests/actual
protoc --plugin=protoc-gen-java-leo=build/protoc-gen-java-leo --java-leo_out=./tests/actual ./tests/addressbook.proto

diff -arq tests/actual tests/expected
exit "$?"