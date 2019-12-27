package de.leohilbert;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;

public abstract class GeneratedMessageLeo extends GeneratedMessageV3 {
    public transient Runnable updateReceiver = null;

    public GeneratedMessageLeo() {
        super();
    }

    public void onChanged() {
        if (updateReceiver != null) {
            updateReceiver.run();
        }
    }

    @Override
    public Message.Builder toBuilder() {
        throw new UnsupportedOperationException("builders are not implemented");
    }

    @Override
    protected Message.Builder newBuilderForType(final BuilderParent parent) {
        throw new UnsupportedOperationException("builders are not implemented");
    }

    @Override
    public Message.Builder newBuilderForType() {
        throw new UnsupportedOperationException("builders are not implemented");
    }
}
