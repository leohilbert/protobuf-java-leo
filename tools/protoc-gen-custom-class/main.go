package main

import (
	"bufio"
	"fmt"
	"io"
	"os"
	"strings"
)

func main() {
	fileName := os.Args[1]
	superClass := os.Args[2]
	f, err := os.Open(fileName)
	check(err)

	messageSet := make(map[string]bool) // New empty set
	reader := bufio.NewReader(f)
	packageName := ""
	for {
		bytes, _, readErr := reader.ReadLine()
		if readErr == io.EOF {
			err = readErr
			return
		}
		check(readErr)

		line := strings.TrimSpace(string(bytes))
		if strings.HasPrefix(line, "package") {
			packageName = line[8 : len(line)-1]
		}
		if strings.HasPrefix(line, "message") {
			cutPrefix := line[8:]
			messageName := cutPrefix[:strings.Index(cutPrefix, " ")]
			messageSet[messageName] = true
		}
		for messageName := range messageSet {
			os.Mkdir("build", 0700)
			customClass, err := os.Create("build/" + messageName + "Custom.java")
			check(err)

			w := bufio.NewWriter(customClass)
			fmt.Fprintf(w, "package %s;\n\n", packageName)
			fmt.Fprintf(w, "public abstract class %sCustom extends %s implements %sInterface<%s> {\n}", messageName, superClass, messageName,messageName)
			err = w.Flush()
			check(err)
		}

	}

}

func check(e error) {
	if e != nil {
		panic(e)
	}
}
