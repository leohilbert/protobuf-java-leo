#!/bin/bash
set -e
mkdir -p build

googleDir=$1
leoDir=$(pwd)

get_latest_release() {
  curl --silent "https://api.github.com/repos/$1/releases/latest" | # Get latest release from GitHub api
    grep '"tag_name":' |                                            # Get tag line
    sed -E 's/.*"([^"]+)".*/\1/'                                    # Pluck JSON value
}

previousCommit=$(<protobuf_commit.txt)
sourcePath="src/google/protobuf/compiler/java"
targetPath="src/google/protobuf/compiler/java_leo"

cd $googleDir
git fetch --all

latestRelease="$(get_latest_release protocolbuffers/protobuf)"
newCommitId=$(git rev-list -n 1 $latestRelease)

git checkout $latestRelease

echo "merging changes from google-repo ($newCommitId)"
git diff -R HEAD $previousCommit -- $sourcePath \
| sed "s~$sourcePath~$targetPath~g" \
> /tmp/leoProtobufPatch.patch

#code /tmp/leoProtobufPatch.patch

cd $leoDir
git apply -3 < /tmp/leoProtobufPatch.patch

echo $newCommitId > protobuf_commit.txt