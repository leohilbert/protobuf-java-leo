FROM debian:buster-slim as buildLeo

WORKDIR /tmp

RUN apt-get update && apt-get install -y wget unzip make cmake g++
COPY ./fetch ./fetch
COPY ./configure.sh .
RUN ./configure.sh

COPY "src" "src"
COPY "CMakeLists.txt" "."
RUN mkdir -p build && cd build && cmake ..
RUN cd build && make -j8

FROM gradle:latest as buildGrpc
WORKDIR /tmp
RUN apt-get update && apt-get install -y git g++
COPY ./docker ./docker
COPY --from=buildLeo ["/tmp/protoc/lib", "/usr/local/lib"]
COPY --from=buildLeo "/tmp/protoc/include" "/usr/local/include"
RUN ldconfig
RUN ./docker/compileGrpc.sh 1.26

FROM debian:buster-slim as final
COPY --from=buildGrpc "/tmp/grpc-java/compiler/build/exe/java_plugin/protoc-gen-grpc-java" "/usr/local/bin"
COPY --from=buildLeo ["/tmp/protoc/lib/libprotobuf.so", "/tmp/protoc/lib/libprotoc.so", "/usr/local/lib/"]
COPY --from=buildLeo ["/tmp/protoc/bin/protoc", "/tmp/build/protoc-gen-java-leo", "/usr/local/bin/"]
COPY --from=buildLeo "/tmp/protoc/include" "/usr/local/include"

RUN chmod -R +x /usr/local/bin
RUN ldconfig

ENTRYPOINT ["protoc"]