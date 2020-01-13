package de.leohilbert.proto;

public interface ProtoEnum {
    static <T extends ProtoEnum> T forNumber(final T[] values, final int readEnum) {
        for (final T value : values) {
            if (value.getProtoNumber() == readEnum) {
                return value;
            }
        }
        return null;
    }

    int getProtoNumber();
}
