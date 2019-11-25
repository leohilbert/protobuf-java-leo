cmake_minimum_required(VERSION 3.15)
project(protoc-gen-java-leo)

set(CMAKE_CXX_STANDARD 17)

file(GLOB SRC "src/google/protobuf/compiler/java_leo/*.cc" "src/javaleo/proto/*.cc")

find_library(PROTOBUF_LITE protobuf-lite)
find_library(PROTOBUF protobuf)
find_library(PROTOC protoc)

add_executable(${CMAKE_PROJECT_NAME} ${SRC} main.cpp)
target_link_libraries(${CMAKE_PROJECT_NAME} ${PROTOBUF_LITE})
target_link_libraries(${CMAKE_PROJECT_NAME} ${PROTOBUF})
target_link_libraries(${CMAKE_PROJECT_NAME} ${PROTOC})

target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE include)
target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE src)