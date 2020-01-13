package de.leohilbert.protoconverter;

import com.example.custom.CustomPhoneType;

public class ProtoConverter_COM_EXAMPLE_CUSTOM_CUSTOMPHONETYPE {
    public static CustomPhoneType fromProto(final int readEnum) {
        return CustomPhoneType.forNumber(readEnum);
    }

    public static int toProto(final CustomPhoneType type_) {
        return type_.protoNumber;
    }
}
