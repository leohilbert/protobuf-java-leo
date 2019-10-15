#include <google/protobuf/compiler/plugin.h>
#include <google/protobuf/compiler/java_leo/java_generator.h>

int main(int argc, char *argv[]) {
    google::protobuf::compiler::java_leo::JavaGenerator generator;
    return google::protobuf::compiler::PluginMain(argc, argv, &generator);
}