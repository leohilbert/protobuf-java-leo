package de.leohilbert.protoconverter;

import java.util.UUID;

public class ProtoConverter_JAVA_UTIL_UUID {
    public static UUID fromProto(final String obj) {
        return UUID.fromString(obj);
    }

    public static String toProto(final UUID obj) {
        return obj.toString();
    }
}
