package com.example.custom;

import com.example.tutorial.PhoneType;
import de.leohilbert.proto.ProtoEnum;

public enum CustomPhoneType implements ProtoEnum {
    MOBILE(PhoneType.MOBILE),
    HOME(PhoneType.HOME),
    WORK(PhoneType.WORK);

    public final int protoNumber;

    CustomPhoneType(final PhoneType proto) {
        this.protoNumber = proto.getNumber();
    }

    public static CustomPhoneType forNumber(final int readEnum) {
        return ProtoEnum.forNumber(values(), readEnum);
    }

    @Override
    public int getProtoNumber() {
        return protoNumber;
    }
}
