cmake_minimum_required(VERSION 3.15)
project(protobuf-leo)

set(CMAKE_CXX_STANDARD 17)

#file(GLOB SRC
#        "src/google/protobuf/compiler/java/*.h"
#        "src/google/protobuf/compiler/java/*.cc"
#        )

find_library(PROTOBUF_LITE protobuf-lite)

add_executable(protobuf-leo ${SRC} main.cpp)
target_link_libraries(protobuf-leo ${PROTOBUF_LITE})
