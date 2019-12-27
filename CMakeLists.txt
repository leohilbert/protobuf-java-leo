cmake_minimum_required(VERSION 3.10)
project(protoc-gen-java-leo)

set(CMAKE_CXX_STANDARD 17)
set(LIB_PATH protoc/bin protoc/lib)

file(GLOB SRC "src/javaleo/proto/*" "src/google/protobuf/compiler/java_leo/*.cc")

add_executable(${CMAKE_PROJECT_NAME} ${SRC} ${CMAKE_SOURCE_DIR}/src/main.cpp)
if (APPLE)
    target_link_libraries(${CMAKE_PROJECT_NAME} ${CMAKE_SOURCE_DIR}/protoc/lib/libprotobuf.dylib)
    target_link_libraries(${CMAKE_PROJECT_NAME} ${CMAKE_SOURCE_DIR}/protoc/lib/libprotoc.dylib)
else ()
    if (UNIX)
        target_link_libraries(${CMAKE_PROJECT_NAME} ${CMAKE_SOURCE_DIR}/protoc/lib/libprotobuf.so)
        target_link_libraries(${CMAKE_PROJECT_NAME} ${CMAKE_SOURCE_DIR}/protoc/lib/libprotoc.so)
        target_link_libraries(${CMAKE_PROJECT_NAME} pthread)
    endif ()
endif ()

target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE protoc/include)
target_include_directories(${CMAKE_PROJECT_NAME} PRIVATE src)