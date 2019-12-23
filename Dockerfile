FROM debian:buster-slim as build

WORKDIR /tmp

RUN apt-get update && apt-get install -y wget unzip make cmake g++
COPY ./fetch ./fetch
COPY ./configure.sh .
RUN ./configure.sh

COPY . .
RUN mkdir -p build && cd build && cmake ..
RUN cd build && make -j8

FROM debian:buster-slim as final
COPY --from=build ["/tmp/protoc/lib/libprotobuf.so", "/tmp/protoc/lib/libprotoc.so", "/usr/local/lib/"]
COPY --from=build ["/tmp/protoc/bin/protoc", "/tmp/build/protoc-gen-java-leo", "/usr/local/bin/"]
COPY --from=build "/tmp/protoc/include" "/usr/local/include"

RUN chmod -R +x /usr/local/bin
RUN ldconfig

ENTRYPOINT ["protoc"]