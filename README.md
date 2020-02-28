Leo's custom java-compiler for Proto-files  
==========================================
based on googles [protoc java compiler](https://github.com/protocolbuffers/protobuf/tree/master/src/google/protobuf/compiler/java).

Motivation
==========================================
I want to use proto-messages both as my internal datamodel as well as the DTOs for my network.
Using protobuf as an internal datamodel has several benefits:
1. **It's really fast.**  
No reflections or complex type-mappings. Just field->bytearray in plain java. Much faster than let's say Hybernate or Java-Ser.
2. **No more wrapper-classes**  
In my current implementation every object in my datamodel has a "toProto" and "fromProto"-method that copies
over the parsed Proto-Messages into the mutable "business-logic" javaclasses and vice-versa. The idea is to use the 
Proto-Messages AS the internal "business-logic" classes as well.
3. **It's simple**  
With serialization you can either have it the easy way (Hibernate, Java-Ser) or the fast way (writing your own serializers).
Since I'm developing a game I need to go for the fast way, but also I do not want to do all the work. 
That's why I'm just generating everything!

Google's implementation of the protobuf java compiler has a few design-choices that are not matching my usecase.  
This is why I'm trying to build my own compiler by copying what google did and modifying it in a few areas so 
it fits my needs.

Features
------------------------------------------
These are the changes compared to Google's implementation:
* All fields now have setters so you can modify them without building a whole new Message-Object
    * This also should make builders optional, since you can directly set the fields on the Message itself.
* Each Message-Class references to a "{MESSAGE_NAME}Custom"-class. 
    * You can use it to implement your own functionality without needing to wrap each Message-object.
    * The Custom-class will not be generated, so your project will only compile 
    if you have them in your project.
* It is possible to directly overwrite the content of an existing Message with another serialized message
    * In googles implementation you always have to create a new message to deserialize a message-binary
* javatype-fieldoption which allows you to directly parse your messages into the desired java-class
    * e.g. a proto "string id" can be a java "java.lang.UUID id" in the generated java-class
    * Converters need to be manually created when used. You will get a compile-error if they don't exist

You can take a look at `/java/src/test` to see it in action.

Use it in your project
==========================================
Maven
------------------------------------------

The easiest way to generated java-classes with this plugin, is by using the Maven-Plugin.  
This is inspired by how GRPC does it.

```
<plugin>
    <groupId>org.xolstice.maven.plugins</groupId>
    <artifactId>protobuf-maven-plugin</artifactId>
    <version>0.6.1</version>

    <configuration>
        <protocArtifact>com.google.protobuf:protoc:3.11.4:exe:${os.detected.classifier}</protocArtifact>
    </configuration>
    <executions>
        <execution>
            <id>leo</id>
            <goals>
                <goal>compile-custom</goal>
                <goal>test-compile-custom</goal>
            </goals>
            <configuration>
                <pluginId>java-leo</pluginId>
                <pluginArtifact>
                    de.leohilbert.protobuf:protobuf-java-leo:${protobufLeo.version}:exe:${os.detected.name}
                </pluginArtifact>
            </configuration>
        </execution>
    </executions>
</plugin>
```

Docker
------------------------------------------

You can also use a docker-image to build it.  
Take a look at the `runAsDocker.sh`. You can also use the images I push to the [DockerHub](https://hub.docker.com/repository/docker/leohilbert/protoc-gen-java-leo).

Compile-Guide
==========================================
* run `./configure.sh`
    * this will download the protoc-libraries into the /protoc-folder
* make sure you have protoc installed in the version used by the plugin 
    * see `/fetch/protoc_release.txt`
    * if you don't want to download it you can copy the content of protoc to `/usr/local`  
* run `./generate.sh` to compile the test-binaries

Updating to the current Protobuf-Release
------------------------------------------
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
