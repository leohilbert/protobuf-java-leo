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

import com.google.protobuf.Descriptors.EnumValueDescriptor;
import com.google.protobuf.Descriptors.FieldDescriptor;
import com.google.protobuf.Descriptors.OneofDescriptor;
import com.google.protobuf.Internal.EnumLite;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * A partial implementation of the {@link Message} interface which implements as many methods of
 * that interface as possible in terms of other methods.
 *
 * @author kenton@google.com Kenton Varda
 */
public abstract class AbstractMessage
    // TODO(dweis): Update GeneratedMessage to parameterize with MessageType and BuilderType.
    implements Message {

  protected int memoizedHashCode = 0;

  @Override
  public boolean isInitialized() {
    return MessageReflection.isInitialized(this);
  }

  @Override
  public List<String> findInitializationErrors() {
    return MessageReflection.findMissingFields(this);
  }

  @Override
  public String getInitializationErrorString() {
    return MessageReflection.delimitWithCommas(findInitializationErrors());
  }

  /** TODO(jieluo): Clear it when all subclasses have implemented this method. */
  @Override
  public boolean hasOneof(OneofDescriptor oneof) {
    throw new UnsupportedOperationException("hasOneof() is not implemented.");
  }

  /** TODO(jieluo): Clear it when all subclasses have implemented this method. */
  @Override
  public FieldDescriptor getOneofFieldDescriptor(OneofDescriptor oneof) {
    throw new UnsupportedOperationException("getOneofFieldDescriptor() is not implemented.");
  }

  @Override
  public String toString() {
    return getClass() + ": not implemented";
  }

  @Override
  public void writeTo(final CodedOutputStream output) throws IOException {
    MessageReflection.writeMessageTo(this, getAllFields(), output, false);
  }

  protected int memoizedSize = -1;

  @Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) {
      return size;
    }

    memoizedSize = MessageReflection.getSerializedSize(this, getAllFields());
    return memoizedSize;
  }

  @Override
  public boolean equals(final Object other) {
    if (other == this) {
      return true;
    }
    if (!(other instanceof Message)) {
      return false;
    }
    final Message otherMessage = (Message) other;
    if (getDescriptorForType() != otherMessage.getDescriptorForType()) {
      return false;
    }
    return compareFields(getAllFields(), otherMessage.getAllFields())
        && getUnknownFields().equals(otherMessage.getUnknownFields());
  }

  @Override
  public int hashCode() {
    int hash = memoizedHashCode;
    if (hash == 0) {
      hash = 41;
      hash = (19 * hash) + getDescriptorForType().hashCode();
      hash = hashFields(hash, getAllFields());
      hash = (29 * hash) + getUnknownFields().hashCode();
      memoizedHashCode = hash;
    }
    return hash;
  }

  private static ByteString toByteString(Object value) {
    if (value instanceof byte[]) {
      return ByteString.copyFrom((byte[]) value);
    } else {
      return (ByteString) value;
    }
  }

  /**
   * Compares two bytes fields. The parameters must be either a byte array or a ByteString object.
   * They can be of different type though.
   */
  private static boolean compareBytes(Object a, Object b) {
    if (a instanceof byte[] && b instanceof byte[]) {
      return Arrays.equals((byte[]) a, (byte[]) b);
    }
    return toByteString(a).equals(toByteString(b));
  }

  /** Converts a list of MapEntry messages into a Map used for equals() and hashCode(). */
  @SuppressWarnings({"rawtypes", "unchecked"})
  private static Map convertMapEntryListToMap(List list) {
    if (list.isEmpty()) {
      return Collections.emptyMap();
    }
    Map result = new HashMap<>();
    Iterator iterator = list.iterator();
    Message entry = (Message) iterator.next();
    Descriptors.Descriptor descriptor = entry.getDescriptorForType();
    Descriptors.FieldDescriptor key = descriptor.findFieldByName("key");
    Descriptors.FieldDescriptor value = descriptor.findFieldByName("value");
    Object fieldValue = entry.getField(value);
    if (fieldValue instanceof EnumValueDescriptor) {
      fieldValue = ((EnumValueDescriptor) fieldValue).getNumber();
    }
    result.put(entry.getField(key), fieldValue);
    while (iterator.hasNext()) {
      entry = (Message) iterator.next();
      fieldValue = entry.getField(value);
      if (fieldValue instanceof EnumValueDescriptor) {
        fieldValue = ((EnumValueDescriptor) fieldValue).getNumber();
      }
      result.put(entry.getField(key), fieldValue);
    }
    return result;
  }

  /** Compares two map fields. The parameters must be a list of MapEntry messages. */
  @SuppressWarnings({"rawtypes", "unchecked"})
  private static boolean compareMapField(Object a, Object b) {
    Map ma = convertMapEntryListToMap((List) a);
    Map mb = convertMapEntryListToMap((List) b);
    return MapFieldLite.equals(ma, mb);
  }

  /**
   * Compares two set of fields. This method is used to implement {@link
   * AbstractMessage#equals(Object)}. It takes
   * special care of bytes fields because immutable messages and mutable messages use different Java
   * type to represent a bytes field and this method should be able to compare immutable messages,
   * mutable messages and also an immutable message to a mutable message.
   */
  static boolean compareFields(Map<FieldDescriptor, Object> a, Map<FieldDescriptor, Object> b) {
    if (a.size() != b.size()) {
      return false;
    }
    for (FieldDescriptor descriptor : a.keySet()) {
      if (!b.containsKey(descriptor)) {
        return false;
      }
      Object value1 = a.get(descriptor);
      Object value2 = b.get(descriptor);
      if (descriptor.getType() == FieldDescriptor.Type.BYTES) {
        if (descriptor.isRepeated()) {
          List list1 = (List) value1;
          List list2 = (List) value2;
          if (list1.size() != list2.size()) {
            return false;
          }
          for (int i = 0; i < list1.size(); i++) {
            if (!compareBytes(list1.get(i), list2.get(i))) {
              return false;
            }
          }
        } else {
          // Compares a singular bytes field.
          if (!compareBytes(value1, value2)) {
            return false;
          }
        }
      } else if (descriptor.isMapField()) {
        if (!compareMapField(value1, value2)) {
          return false;
        }
      } else {
        // Compare non-bytes fields.
        if (!value1.equals(value2)) {
          return false;
        }
      }
    }
    return true;
  }

  /** Calculates the hash code of a map field. {@code value} must be a list of MapEntry messages. */
  @SuppressWarnings("unchecked")
  private static int hashMapField(Object value) {
    return MapFieldLite.calculateHashCodeForMap(convertMapEntryListToMap((List) value));
  }

  /** Get a hash code for given fields and values, using the given seed. */
  @SuppressWarnings("unchecked")
  protected static int hashFields(int hash, Map<FieldDescriptor, Object> map) {
    for (Map.Entry<FieldDescriptor, Object> entry : map.entrySet()) {
      FieldDescriptor field = entry.getKey();
      Object value = entry.getValue();
      hash = (37 * hash) + field.getNumber();
      if (field.isMapField()) {
        hash = (53 * hash) + hashMapField(value);
      } else if (field.getType() != FieldDescriptor.Type.ENUM) {
        hash = (53 * hash) + value.hashCode();
      } else if (field.isRepeated()) {
        List<? extends EnumLite> list = (List<? extends EnumLite>) value;
        hash = (53 * hash) + Internal.hashEnumList(list);
      } else {
        hash = (53 * hash) + Internal.hashEnum((EnumLite) value);
      }
    }
    return hash;
  }

  // =================================================================

  /**
   * @deprecated from v3.0.0-beta-3+, for compatibility with v2.5.0 and v2.6.1
   * generated code.
   */
  @Deprecated
  protected static int hashLong(long n) {
    return (int) (n ^ (n >>> 32));
  }
  //
  /**
   * @deprecated from v3.0.0-beta-3+, for compatibility with v2.5.0 and v2.6.1
   * generated code.
   */
  @Deprecated
  protected static int hashBoolean(boolean b) {
    return b ? 1231 : 1237;
  }
  //
  /**
   * @deprecated from v3.0.0-beta-3+, for compatibility with v2.5.0 and v2.6.1
   * generated code.
   */
  @Deprecated
  protected static int hashEnum(EnumLite e) {
    return e.getNumber();
  }
  //
  /**
   * @deprecated from v3.0.0-beta-3+, for compatibility with v2.5.0 and v2.6.1
   * generated code.
   */
  @Deprecated
  protected static int hashEnumList(List<? extends EnumLite> list) {
    int hash = 1;
    for (EnumLite e : list) {
      hash = 31 * hash + hashEnum(e);
    }
    return hash;
  }
}
