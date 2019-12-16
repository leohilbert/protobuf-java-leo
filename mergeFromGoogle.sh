#!/bin/bash
set -e

# --------------------------------------------------------------------------------
# This script will search the newest release from the official protobuf-repo
# and merge all changes since the last execution to the java-compiler into the customized codebase.
# Afterwards it triggers the "configuration.sh" to rebuild the protobuf-binaries with this release.
# Requires google's protobuf-repo to be cloned somewhere.
#
# example usage: ./mergeFromGoogle.sh /path/to/protobuf
# --------------------------------------------------------------------------------

# specifies your local git-repo of the protobuf-project,
# so the patch can be generated from there.
googleDir=$1
leoDir=$(pwd)
previousCommit=$(<fetch/protoc_commit.txt)

if [ -z $googleDir ]; then
  echo "googleDir not specified"
  exit 1
fi

cd $googleDir || exit 2
git fetch --all

# find latest protobuf release
latestRelease=$(curl --silent "https://api.github.com/repos/protocolbuffers/protobuf/releases/latest" |
  grep '"tag_name":' | sed -E 's/.*"([^"]+)".*/\1/')

git checkout $latestRelease
newCommitId=$(git rev-list -n 1 HEAD)

sourcePath="src/google/protobuf/compiler/java"
targetPath="src/google/protobuf/compiler/java_leo"
patchTempDir="/tmp/leoProtobufPatch.patch"

echo "merging changes from google-repo ($newCommitId)"
git diff -R HEAD $previousCommit -- $sourcePath |
  sed "s~$sourcePath~$targetPath~g" \
    >$patchTempDir

#code $patchTempDir

cd $leoDir || exit 3
if [ -s $patchTempDir ]; then git apply -3 <$patchTempDir; fi

echo "$newCommitId" >fetch/protoc_commit.txt
echo "$latestRelease" >fetch/protoc_release.txt

./configure.sh $googleDir
