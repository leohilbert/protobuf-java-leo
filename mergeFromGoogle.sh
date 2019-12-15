#!/bin/bash
set -e
mkdir -p build

previousCommit=$(<protobuf_commit.txt)
sourcePath="src/google/protobuf/compiler/java"
targetPath="src/google/protobuf/compiler/java_leo"

git remote add protobuf https://github.com/protocolbuffers/protobuf || true
git fetch protobuf
newCommitId=$(git rev-parse protobuf/master)

echo "merging changes from google-repo ($newCommitId)"
git diff -R protobuf/master $previousCommit -- $sourcePath \
| sed "s~$sourcePath~$targetPath~g" \
| git apply -3
#| code -

echo $newCommitId > protobuf_commit.txt