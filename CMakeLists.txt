cmake_minimum_required(VERSION 3.12)
project(protoc-gen-java-leo)

set(CMAKE_CXX_STANDARD 17)
set(LIB_PATH protoc/lib)

file(GLOB SRC "src/javaleo/proto/*" "src/google/protobuf/compiler/java_leo/*.cc")

find_library(PROTOBUF NAMES protobuf PATHS ${LIB_PATH} NO_DEFAULT_PATH)
find_library(PROTOC NAMES protoc PATHS ${LIB_PATH} NO_DEFAULT_PATH)

message("Using ${PROTOBUF}")
message("Using ${PROTOC}")

add_executable(${CMAKE_PROJECT_NAME} ${SRC} main.cpp)
target_link_libraries(${CMAKE_PROJECT_NAME} ${PROTOBUF})
target_link_libraries(${CMAKE_PROJECT_NAME} ${PROTOC})

target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE protoc/include)
target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE src)