#include <google/protobuf/compiler/plugin.h>
#include <google/protobuf/compiler/java_leo/java_generator.h>

int main(int argc, char* argv[]) {
  if (argc > 1 && std::string(argv[1]) == "debugInput") {
    freopen("../generated/code_generator_request.pb.bin", "r", stdin);
    argc = 1;
  }
  google::protobuf::compiler::java_leo::JavaGenerator generator;
  return google::protobuf::compiler::PluginMain(argc, argv, &generator);
}