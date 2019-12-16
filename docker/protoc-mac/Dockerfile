FROM multiarch/crossbuild
ARG PROTOBUF_VERSION
ENV OUTDIR=/out
ENV CROSS_TRIPLE=x86_64-apple-darwin

RUN git clone https://github.com/bit-spark/objective-c-hello-world
RUN mkdir -p /protobuf && \
        curl -L https://github.com/google/protobuf/archive/${PROTOBUF_VERSION}.tar.gz | tar xvz --strip-components=1 -C /protobuf
RUN cd /protobuf && \
        autoreconf -f -i -Wall,no-obsolete && \
        ./configure --prefix=/usr --enable-static=no && \
        crossbuild make -j2 && \
        crossbuild make install
RUN cd /protobuf && \
        crossbuild make install DESTDIR=${OUTDIR}

ENTRYPOINT ["/usr/bin/protoc", "-I/protobuf"]

# library /out/usr/lib/
# headers /out/usr/include/
# binary /out/usr/bin