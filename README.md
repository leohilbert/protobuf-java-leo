Leo's custom java-compiler for Proto-files (work in progress!)  
==========================================
based on googles [protoc java compiler](https://github.com/protocolbuffers/protobuf/tree/master/src/google/protobuf/compiler/java).

Example usage (execute in project-root)
---------------------------------
```
protoc --plugin=protoc-gen-java-leo=build/protoc-gen-java-leo --java-leo_out=./build ./examples/addressbook.proto
```

