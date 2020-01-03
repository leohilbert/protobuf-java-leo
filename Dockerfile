FROM debian:buster-slim as buildLeo

WORKDIR /tmp

RUN apt-get update && apt-get install -y wget unzip make cmake g++
COPY ./fetch ./fetch
COPY ./configure.sh .
RUN ./configure.sh

COPY "src" "src"
COPY "CMakeLists.txt" "."
RUN mkdir -p build \
    && cd build && cmake .. \
    && make -j$(nproc)

FROM gradle:6.0-jre13 as buildGrpc
WORKDIR /tmp
ARG GRPC_JAVA_VERSION=1.26.x
RUN apt-get update && apt-get install -y git g++
COPY --from=buildLeo ["/tmp/protoc/lib", "/usr/local/lib"]
COPY --from=buildLeo "/tmp/protoc/include" "/usr/local/include"
RUN ldconfig; set -x \
    && git clone -b v${GRPC_JAVA_VERSION} --recursive https://github.com/grpc/grpc-java.git grpc-java \
    && cd grpc-java/compiler \
    && gradle java_pluginExecutable

FROM debian:buster-slim as final
COPY --from=buildGrpc "/tmp/grpc-java/compiler/build/exe/java_plugin/protoc-gen-grpc-java" "/usr/local/bin"
COPY --from=buildLeo ["/tmp/protoc/lib/libprotobuf.so", "/tmp/protoc/lib/libprotoc.so", "/usr/local/lib/"]
COPY --from=buildLeo ["/tmp/protoc/bin/protoc", "/tmp/build/protoc-gen-java-leo", "/usr/local/bin/"]
COPY --from=buildLeo "/tmp/protoc/include" "/usr/local/include"

RUN chmod -R +x /usr/local/bin
RUN ldconfig

ENTRYPOINT ["/bin/sh"]
CMD ["protoc"]