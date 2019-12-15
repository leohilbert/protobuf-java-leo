FROM alpine:3.8 as protoc_builder
RUN apk add --no-cache build-base curl automake autoconf libtool git zlib-dev

ENV PROTOBUF_VERSION=3.6.1 OUTDIR=/out
RUN mkdir -p /protobuf && \
        curl -L https://github.com/google/protobuf/archive/v${PROTOBUF_VERSION}.tar.gz | tar xvz --strip-components=1 -C /protobuf
RUN cd /protobuf && \
        autoreconf -f -i -Wall,no-obsolete && \
        ./configure --prefix=/usr --enable-static=no && \
        make -j2 && make install
RUN cd /protobuf && \
        make install DESTDIR=${OUTDIR}
#RUN find ${OUTDIR} -name "*.a" -delete -or -name "*.la" -delete

ENTRYPOINT ["/usr/bin/protoc", "-I/protobuf"]