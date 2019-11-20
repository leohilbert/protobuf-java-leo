// protoc-gen-debug emits the raw encoded CodeGeneratorRequest from a protoc
// execution to a file. This is particularly useful for testing (see the
// testdata/graph package for test cases).
package main

import (
	"bytes"
	"io"
	"io/ioutil"
	"log"
	"os"
	"path/filepath"

	"github.com/golang/protobuf/proto"
	plugin_go "github.com/golang/protobuf/protoc-gen-go/plugin"
)

// copied from https://github.com/lyft/protoc-gen-star/tree/master/protoc-gen-debug (thanks!! :)
// and modified, since the original does not work with the c++ plugin-implementation
func main() {
	data, err := ioutil.ReadAll(os.Stdin)
	if err != nil {
		log.Fatal("unable to read input: ", err)
	}
	err = ioutil.WriteFile(filepath.Join("./generated/code_generator_request.pb.bin"), data, 0644)
	if err != nil {
		log.Fatal("unable to write request to disk: ", err)
	}

	data, err = proto.Marshal(&plugin_go.CodeGeneratorResponse{})
	if err != nil {
		log.Fatal("unable to marshal response payload: ", err)
	}

	_, err = io.Copy(os.Stdout, bytes.NewReader(data))
	if err != nil {
		log.Fatal("unable to write response to stdout: ", err)
	}
}
