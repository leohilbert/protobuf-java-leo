FROM ubuntu:18.04 as build

WORKDIR /tmp

RUN apt-get update && apt-get install -y wget unzip make cmake g++
COPY ./fetch ./fetch
COPY ./configure.sh .
RUN ./configure.sh

COPY . /tmp
RUN mkdir -p build && cd build && cmake ..
RUN cd build && make

FROM debian:buster-slim as final
COPY --from=build /tmp/protoc/bin /usr/local/bin
COPY --from=build /tmp/protoc/lib/*.so.* /usr/local/lib/
COPY --from=build /tmp/build/protoc-gen-java-leo /usr/local/bin

RUN chmod -R +x /usr/local/bin
RUN ldconfig

ENTRYPOINT ["protoc", " --plugin=protoc-gen-java-leo=protoc-gen-java-leo"]