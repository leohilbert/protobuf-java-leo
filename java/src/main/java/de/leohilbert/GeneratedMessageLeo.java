package de.leohilbert;

import com.google.protobuf.GeneratedMessageV3;

public abstract class GeneratedMessageLeo extends GeneratedMessageV3 {
    public GeneratedMessageLeo() {
        super();
    }

    protected void fromBuilder(Builder<?> builder) {
        unknownFields = builder.getUnknownFields();
    }

    public void onChanged() {

    }
}
