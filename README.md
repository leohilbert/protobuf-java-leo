Leo's custom java-compiler for Proto-files (work in progress!)  
==========================================
based on googles [protoc java compiler](https://github.com/protocolbuffers/protobuf/tree/master/src/google/protobuf/compiler/java).

Example usage (execute in project-root)
---------------------------------
## ./generate.sh  
Compiles the project and generates the javaclasses for my test-proto file into ./generated

## ./createDebugInput.sh  
Will use protoc on the test-proto file and store the CodeGenerationRequest in text-form in ./generated/code_generator_request.pb.bin 
This allows you to debug the plugin by adding "debugInput" as a program argument (see main.cpp)
