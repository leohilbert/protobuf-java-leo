FROM alpine:3.8
RUN apk add --no-cache build-base curl automake autoconf libtool git zlib-dev

ARG PROTOBUF_VERSION
ENV OUTDIR=/out
RUN mkdir -p /protobuf && \
        curl -L https://github.com/google/protobuf/archive/${PROTOBUF_VERSION}.tar.gz | tar xvz --strip-components=1 -C /protobuf
RUN cd /protobuf && \
        autoreconf -f -i -Wall,no-obsolete && \
        ./configure --prefix=/usr --enable-static=no && \
        make -j2 && make install
RUN cd /protobuf && \
        make install DESTDIR=${OUTDIR}

ENTRYPOINT ["/usr/bin/protoc", "-I/protobuf"]

# library /out/usr/lib/
# headers /out/usr/include/
# binary /out/usr/bin