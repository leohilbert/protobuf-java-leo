Leo's custom java-compiler for Proto-files (work in progress!)  
==========================================
based on googles [protoc java compiler](https://github.com/protocolbuffers/protobuf/tree/master/src/google/protobuf/compiler/java).

Installation-Guide
==========================================
* run `./configure.sh`
    * this will download the protoc-libraries into the /protoc-folder
* make sure you have protoc installed in the used version 
    * see `/fetch/protoc_release.txt`
    * if you don't want to download it you can but the content of protoc in `/usr/local`  
* run `./generate.sh` to compile the test-binaries

Updating to the current Protobuf-Release
==========================================
First you need to make sure that the newest release is also build in my custom protobuf-compile repo [here](https://github.com/leohilbert/protobuf-compile/) 

Also you need to clone google's protobuf-project somewhere locally.  
After that you should only need to run 
`./mergeFromGoogle.sh /path/to/protobuf`. 
This will
1. checkout the latest release in you protobuf-repo 
2. diff the changes to the java-compiler compared to the last diffed release
3. apply the diff to the  `/src/google/protobuf/compiler/java_leo` in this project
4. write the newly fetched commit&tag into the fetch-folder
5. run the configure & generate script
