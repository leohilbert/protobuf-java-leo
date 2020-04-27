#!/bin/bash
set -e
# --------------------------------------------------------------------------------
# Downloads the current supported protoc-release to place it into the "protoc"-folder.
# This binary should be used during compile to ensure the same protoc-binary&libraries is used.
# --------------------------------------------------------------------------------

latestProtocRelease=$(<fetch/protoc_release.txt)

case "$(uname -s)" in
Darwin)
  os='macos'
  ;;
Linux)
  os='linux'
  ;;
CYGWIN* | MINGW32* | MSYS*)
  os='windows'
  ;;
esac

url=https://github.com/leohilbert/protobuf-compile/releases/download/$latestProtocRelease/protoc-$latestProtocRelease-$os.zip
echo "Downloading $url"
rm -rf ./protoc && mkdir ./protoc

wget $url -nv -O protoc.zip
unzip -q protoc.zip -d protoc
rm protoc.zip
chmod +x protoc/bin/protoc