Leo's custom java-compiler for Proto-files (work in progress!)  
==========================================

Example (execute in project-root)
---------------------------------
```
protoc --plugin=protoc-gen-java-leo=build/protoc-gen-java-leo --java-leo_out=./build ./examples/addressbook.proto
```