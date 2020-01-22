// Protocol Buffers - Google's data interchange format
// Copyright 2008 Google Inc.  All rights reserved.
// https://developers.google.com/protocol-buffers/
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are
// met:
//
//     * Redistributions of source code must retain the above copyright
// notice, this list of conditions and the following disclaimer.
//     * Redistributions in binary form must reproduce the above
// copyright notice, this list of conditions and the following disclaimer
// in the documentation and/or other materials provided with the
// distribution.
//     * Neither the name of Google Inc. nor the names of its
// contributors may be used to endorse or promote products derived from
// this software without specific prior written permission.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
// "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
// LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
// OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
// SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
// LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
// DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
// THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
// (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
// OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

package com.google.protobuf;

import com.google.protobuf.Descriptors.Descriptor;
import com.google.protobuf.Descriptors.EnumDescriptor;
import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.FileDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.Internal.BooleanList;
import com.google.protobuf.Internal.DoubleList;
import com.google.protobuf.Internal.FloatList;
import com.google.protobuf.Internal.IntList;
import com.google.protobuf.Internal.LongList;
// In opensource protobuf, we have versioned this GeneratedMessageV3 class to GeneratedMessageV3V3 and
// in the future may have GeneratedMessageV3V4 etc. This allows us to change some aspects of this
// class without breaking binary compatibility with old generated code that still subclasses
// the old GeneratedMessageV3 class. To allow these different GeneratedMessageV3V? classes to
// interoperate (e.g., a GeneratedMessageV3V3 object has a message extension field whose class
// type is GeneratedMessageV3V4), these classes still share a common parent class AbstractMessage
// and are using the same GeneratedMessage.GeneratedExtension class for extension definitions.
// Since this class becomes GeneratedMessageV3V? in opensource, we have to add an import here
// to be able to use GeneratedMessage.GeneratedExtension. The GeneratedExtension definition in
// this file is also excluded from opensource to avoid conflict.
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * All generated protocol message classes extend this class.  This class
 * implements most of the Message and Builder interfaces using Java reflection.
 * Users can ignore this class and pretend that generated messages implement
 * the Message interface directly.
 *
 * @author kenton@google.com Kenton Varda
 */
public abstract class GeneratedMessageV3 extends AbstractMessage
    implements Serializable {
  private static final long serialVersionUID = 1L;

  /**
   * For testing. Allows a test to disable the optimization that avoids using field builders for
   * nested messages until they are requested. By disabling this optimization, existing tests can be
   * reused to test the field builders.
   */
  protected static boolean alwaysUseFieldBuilders = false;

  /** For use by generated code only.  */
  protected UnknownFieldSet unknownFields;

  protected GeneratedMessageV3() {
    unknownFields = UnknownFieldSet.getDefaultInstance();
  }

  @Override
  public Parser<? extends GeneratedMessageV3> getParserForType() {
    throw new UnsupportedOperationException(
        "This is supposed to be overridden by subclasses.");
  }

 /**
  * @see #setAlwaysUseFieldBuildersForTesting(boolean)
  */
  static void enableAlwaysUseFieldBuildersForTesting() {
    setAlwaysUseFieldBuildersForTesting(true);
  }

  /**
   * For testing. Allows a test to disable/re-enable the optimization that avoids
   * using field builders for nested messages until they are requested. By disabling
   * this optimization, existing tests can be reused to test the field builders.
   */
  static void setAlwaysUseFieldBuildersForTesting(boolean useBuilders) {
    alwaysUseFieldBuilders = useBuilders;
  }

  protected void mergeFromAndMakeImmutableInternal(
          CodedInputStream input)
      throws InvalidProtocolBufferException {
    Schema<GeneratedMessageV3> schema =
        (Schema<GeneratedMessageV3>) Protobuf.getInstance().schemaFor(this);
    try {
      schema.mergeFrom(this, CodedInputStreamReader.forCodedInput(input));
    } catch (InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (IOException e) {
      throw new InvalidProtocolBufferException(e).setUnfinishedMessage(this);
    }
    schema.makeImmutable(this);
  }

  @Override
  public boolean isInitialized() {
    for (final FieldDescriptor field : getDescriptorForType().getFields()) {
      // Check that all required fields are present.
      if (field.isRequired()) {
        if (!hasField(field)) {
          return false;
        }
      }
      // Check that embedded messages are initialized.
      if (field.getJavaType() == FieldDescriptor.JavaType.MESSAGE) {
        if (field.isRepeated()) {
          @SuppressWarnings("unchecked") final
          List<Message> messageList = (List<Message>) getField(field);
          for (final Message element : messageList) {
            if (!element.isInitialized()) {
              return false;
            }
          }
        } else {
          if (hasField(field) && !((Message) getField(field)).isInitialized()) {
            return false;
          }
        }
      }
    }

    return true;
  }

  @Override
  public UnknownFieldSet getUnknownFields() {
    throw new UnsupportedOperationException(
        "This is supposed to be overridden by subclasses.");
  }

  /**
   * Called by subclasses to parse an unknown field.
   *
   * @return {@code true} unless the tag is an end-group tag.
   */
  protected boolean parseUnknownField(
          CodedInputStream input,
          UnknownFieldSet.Builder unknownFields,
          int tag)
      throws IOException {
    if (input.shouldDiscardUnknownFields()) {
      return input.skipField(tag);
    }
    return unknownFields.mergeFieldFrom(tag, input);
  }

  /**
   * Delegates to parseUnknownField. This method is obsolete, but we must retain it for
   * compatibility with older generated code.
   */
  protected boolean parseUnknownFieldProto3(
          CodedInputStream input,
          UnknownFieldSet.Builder unknownFields,
          int tag)
      throws IOException {
    return parseUnknownField(input, unknownFields, tag);
  }

  protected static <M extends Message> M parseWithIOException(Parser<M> parser, InputStream input)
      throws IOException {
    try {
      return parser.parseFrom(input);
    } catch (InvalidProtocolBufferException e) {
      throw e.unwrapIOException();
    }
  }

  protected static <M extends Message> M parseDelimitedWithIOException(Parser<M> parser,
      InputStream input) throws IOException {
    try {
      return parser.parseDelimitedFrom(input);
    } catch (InvalidProtocolBufferException e) {
      throw e.unwrapIOException();
    }
  }

  protected static boolean canUseUnsafe() {
    return UnsafeUtil.hasUnsafeArrayOperations() && UnsafeUtil.hasUnsafeByteBufferOperations();
  }

  protected static IntList emptyIntList() {
    return IntArrayList.emptyList();
  }

  protected static IntList newIntList() {
    return new IntArrayList();
  }

  protected static IntList mutableCopy(IntList list) {
    int size = list.size();
    return list.mutableCopyWithCapacity(
        size == 0 ? AbstractProtobufList.DEFAULT_CAPACITY : size * 2);
  }

  protected static LongList emptyLongList() {
    return LongArrayList.emptyList();
  }

  protected static LongList newLongList() {
    return new LongArrayList();
  }

  protected static LongList mutableCopy(LongList list) {
    int size = list.size();
    return list.mutableCopyWithCapacity(
        size == 0 ? AbstractProtobufList.DEFAULT_CAPACITY : size * 2);
  }

  protected static FloatList emptyFloatList() {
    return FloatArrayList.emptyList();
  }

  protected static FloatList newFloatList() {
    return new FloatArrayList();
  }

  protected static FloatList mutableCopy(FloatList list) {
    int size = list.size();
    return list.mutableCopyWithCapacity(
        size == 0 ? AbstractProtobufList.DEFAULT_CAPACITY : size * 2);
  }

  protected static DoubleList emptyDoubleList() {
    return DoubleArrayList.emptyList();
  }

  protected static DoubleList newDoubleList() {
    return new DoubleArrayList();
  }

  protected static DoubleList mutableCopy(DoubleList list) {
    int size = list.size();
    return list.mutableCopyWithCapacity(
        size == 0 ? AbstractProtobufList.DEFAULT_CAPACITY : size * 2);
  }

  protected static BooleanList emptyBooleanList() {
    return BooleanArrayList.emptyList();
  }

  protected static BooleanList newBooleanList() {
    return new BooleanArrayList();
  }

  protected static BooleanList mutableCopy(BooleanList list) {
    int size = list.size();
    return list.mutableCopyWithCapacity(
        size == 0 ? AbstractProtobufList.DEFAULT_CAPACITY : size * 2);
  }

  /**
   * This class is used to make a generated protected method inaccessible from user's code (e.g.,
   * the {@link #newInstance} method below). When this class is used as a parameter's type in a
   * generated protected method, the method is visible to user's code in the same package, but
   * since the constructor of this class is private to protobuf runtime, user's code can't obtain
   * an instance of this class and as such can't actually make a method call on the protected
   * method.
   */
  protected static final class UnusedPrivateParameter {
    static final UnusedPrivateParameter INSTANCE = new UnusedPrivateParameter();

    private UnusedPrivateParameter() {
    }
  }

  /**
   * Creates a new instance of this message type. Overridden in the generated code.
   */
  @SuppressWarnings({"unused"})
  protected Object newInstance(UnusedPrivateParameter unused) {
    throw new UnsupportedOperationException("This method must be overridden by the subclass.");
  }

  /**
   * Used by parsing constructors in generated classes.
   */
  protected void makeExtensionsImmutable() {
    // Noop for messages without extensions.
  }

  /**
   * TODO(xiaofeng): remove this after b/29368482 is fixed. We need to move this
   * interface to AbstractMessage in order to versioning GeneratedMessageV3 but
   * this move breaks binary compatibility for AppEngine. After AppEngine is
   * fixed we can exlude this from google3.
   */
  protected interface BuilderParent extends AbstractMessage.BuilderParent {}

  // =================================================================

  /**
   * Gets the map field with the given field number. This method should be overridden in the
   * generated message class if the message contains map fields.
   *
   * <p>Unlike other field types, reflection support for map fields can't be implemented based on
   * generated public API because we need to access a map field as a list in reflection API but the
   * generated API only allows us to access it as a map. This method returns the underlying map
   * field directly and thus enables us to access the map field as a list.
   */
  @SuppressWarnings({"rawtypes", "unused"})
  protected MapField internalGetMapField(int fieldNumber) {
    // Note that we can't use descriptor names here because this method will
    // be called when descriptor is being initialized.
    throw new RuntimeException(
        "No map fields found in " + getClass().getName());
  }

  protected static int computeStringSize(final int fieldNumber, final Object value) {
    if (value instanceof String) {
      return CodedOutputStream.computeStringSize(fieldNumber, (String) value);
    } else {
      return CodedOutputStream.computeBytesSize(fieldNumber, (ByteString) value);
    }
  }

  protected static int computeStringSizeNoTag(final Object value) {
    if (value instanceof String) {
      return CodedOutputStream.computeStringSizeNoTag((String) value);
    } else {
      return CodedOutputStream.computeBytesSizeNoTag((ByteString) value);
    }
  }

  protected static void writeString(
      CodedOutputStream output, final int fieldNumber, final Object value) throws IOException {
    if (value instanceof String) {
      output.writeString(fieldNumber, (String) value);
    } else {
      output.writeBytes(fieldNumber, (ByteString) value);
    }
  }

  protected static void writeStringNoTag(
      CodedOutputStream output, final Object value) throws IOException {
    if (value instanceof String) {
      output.writeStringNoTag((String) value);
    } else {
      output.writeBytesNoTag((ByteString) value);
    }
  }

  protected static <V> void serializeIntegerMapTo(
      CodedOutputStream out,
      MapField<Integer, V> field,
      MapEntry<Integer, V> defaultEntry,
      int fieldNumber) throws IOException {
    Map<Integer, V> m = field.getMap();
    if (!out.isSerializationDeterministic()) {
      serializeMapTo(out, m, defaultEntry, fieldNumber);
      return;
    }
    // Sorting the unboxed keys and then look up the values during serialziation is 2x faster
    // than sorting map entries with a custom comparator directly.
    int[] keys = new int[m.size()];
    int index = 0;
    for (int k : m.keySet()) {
      keys[index++] = k;
    }
    Arrays.sort(keys);
    for (int key : keys) {
      out.writeMessage(fieldNumber,
          defaultEntry.newBuilderForType()
              .setKey(key)
              .setValue(m.get(key))
              .build());
    }
  }

  protected static <V> void serializeLongMapTo(
      CodedOutputStream out,
      MapField<Long, V> field,
      MapEntry<Long, V> defaultEntry,
      int fieldNumber)
      throws IOException {
    Map<Long, V> m = field.getMap();
    if (!out.isSerializationDeterministic()) {
      serializeMapTo(out, m, defaultEntry, fieldNumber);
      return;
    }

    long[] keys = new long[m.size()];
    int index = 0;
    for (long k : m.keySet()) {
      keys[index++] = k;
    }
    Arrays.sort(keys);
    for (long key : keys) {
      out.writeMessage(fieldNumber,
          defaultEntry.newBuilderForType()
              .setKey(key)
              .setValue(m.get(key))
              .build());
    }
  }

  protected static <V> void serializeStringMapTo(
      CodedOutputStream out,
      MapField<String, V> field,
      MapEntry<String, V> defaultEntry,
      int fieldNumber)
      throws IOException {
    Map<String, V> m = field.getMap();
    if (!out.isSerializationDeterministic()) {
      serializeMapTo(out, m, defaultEntry, fieldNumber);
      return;
    }

    // Sorting the String keys and then look up the values during serialziation is 25% faster than
    // sorting map entries with a custom comparator directly.
    String[] keys = new String[m.size()];
    keys = m.keySet().toArray(keys);
    Arrays.sort(keys);
    for (String key : keys) {
      out.writeMessage(fieldNumber,
          defaultEntry.newBuilderForType()
              .setKey(key)
              .setValue(m.get(key))
              .build());
    }
  }

  protected static <V> void serializeBooleanMapTo(
      CodedOutputStream out,
      MapField<Boolean, V> field,
      MapEntry<Boolean, V> defaultEntry,
      int fieldNumber)
      throws IOException {
    Map<Boolean, V> m = field.getMap();
    if (!out.isSerializationDeterministic()) {
      serializeMapTo(out, m, defaultEntry, fieldNumber);
      return;
    }
    maybeSerializeBooleanEntryTo(out, m, defaultEntry, fieldNumber, false);
    maybeSerializeBooleanEntryTo(out, m, defaultEntry, fieldNumber, true);
  }

  private static <V> void maybeSerializeBooleanEntryTo(
      CodedOutputStream out,
      Map<Boolean, V> m,
      MapEntry<Boolean, V> defaultEntry,
      int fieldNumber,
      boolean key)
      throws IOException {
    if (m.containsKey(key)) {
      out.writeMessage(fieldNumber,
          defaultEntry.newBuilderForType()
              .setKey(key)
              .setValue(m.get(key))
              .build());
    }
  }

  /** Serialize the map using the iteration order. */
  private static <K, V> void serializeMapTo(
      CodedOutputStream out,
      Map<K, V> m,
      MapEntry<K, V> defaultEntry,
      int fieldNumber)
      throws IOException {
    for (Map.Entry<K, V> entry : m.entrySet()) {
      out.writeMessage(fieldNumber,
          defaultEntry.newBuilderForType()
              .setKey(entry.getKey())
              .setValue(entry.getValue())
              .build());
    }
  }

  // LEOS STUFF
  public transient Runnable updateReceiver = null;

  public void onChanged(int fieldNumber) {
      memoizedSize = -1;
      memoizedHashCode = -1;
      if (updateReceiver != null) {
          updateReceiver.run();
      }
  }

  public void afterMessageInit() {
  }

  public void afterMessageUpdate() {
  }

  public abstract void clear();
}

