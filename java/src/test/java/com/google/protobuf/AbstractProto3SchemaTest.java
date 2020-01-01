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

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.google.protobuf.testing.Proto3Testing.Proto3Empty;
import com.google.protobuf.testing.Proto3Testing.Proto3Message;
import com.google.protobuf.testing.Proto3Testing.Proto3MessageWithMaps;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;

/** Base class for tests using {@link Proto3Message}. */
public abstract class AbstractProto3SchemaTest extends AbstractSchemaTest<Proto3Message> {
  @Override
  protected Proto3MessageFactory messageFactory() {
    return new Proto3MessageFactory(10, 20, 2, 2);
  }

  @Override
  protected List<ByteBuffer> serializedBytesWithInvalidUtf8() throws IOException {
    List<ByteBuffer> invalidBytes = new ArrayList<>();
    byte[] invalid = new byte[] {(byte) 0x80};
    {
      ByteBuffer buffer = ByteBuffer.allocate(100);
      CodedOutputStream codedOutput = CodedOutputStream.newInstance(buffer);
      codedOutput.writeByteArray(Proto3Message.FIELD_STRING_9_FIELD_NUMBER, invalid);
      codedOutput.flush();
      buffer.flip();
      invalidBytes.add(buffer);
    }
    {
      ByteBuffer buffer = ByteBuffer.allocate(100);
      CodedOutputStream codedOutput = CodedOutputStream.newInstance(buffer);
      codedOutput.writeByteArray(Proto3Message.FIELD_STRING_LIST_26_FIELD_NUMBER, invalid);
      codedOutput.flush();
      buffer.flip();
      invalidBytes.add(buffer);
    }
    return invalidBytes;
  }

  @Test
  public void mergeOptionalMessageFields() throws Exception {
    Proto3Message message1 =
        new Proto3Message()
            .setFieldMessage10(new Proto3Message().setFieldInt643(123).clearFieldInt325())
            ;
    Proto3Message message2 =
        new Proto3Message()
            .setFieldMessage10(new Proto3Message().clearFieldInt643().setFieldInt325(456))
            ;
    Proto3Message message3 =
        new Proto3Message()
            .setFieldMessage10(new Proto3Message().setFieldInt643(789).clearFieldInt325())
            ;
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    message1.writeTo(output);
    message2.writeTo(output);
    message3.writeTo(output);
    byte[] data = output.toByteArray();

    Proto3Message merged = ExperimentalSerializationUtil.fromByteArray(data, Proto3Message.class);
    assertEquals(789, merged.getFieldMessage10().getFieldInt643());
    assertEquals(456, merged.getFieldMessage10().getFieldInt325());
  }

  @Test
  public void oneofFieldsShouldRoundtrip() throws IOException {
    roundtrip("Field 53", new Proto3Message().setFieldDouble53(100));
    roundtrip("Field 54", new Proto3Message().setFieldFloat54(100));
    roundtrip("Field 55", new Proto3Message().setFieldInt6455(100));
    roundtrip("Field 56", new Proto3Message().setFieldUint6456(100L));
    roundtrip("Field 57", new Proto3Message().setFieldInt3257(100));
    roundtrip("Field 58", new Proto3Message().setFieldFixed6458(100));
    roundtrip("Field 59", new Proto3Message().setFieldFixed3259(100));
    roundtrip("Field 60", new Proto3Message().setFieldBool60(true));
    roundtrip("Field 61", new Proto3Message().setFieldString61(data().getString()));
    roundtrip(
        "Field 62", new Proto3Message().setFieldMessage62(new Proto3Message().setFieldDouble1(100)));
    roundtrip("Field 63", new Proto3Message().setFieldBytes63(data().getBytes()));
    roundtrip("Field 64", new Proto3Message().setFieldUint3264(100));
    roundtrip("Field 65", new Proto3Message().setFieldSfixed3265(100));
    roundtrip("Field 66", new Proto3Message().setFieldSfixed6466(100));
    roundtrip("Field 67", new Proto3Message().setFieldSint3267(100));
    roundtrip("Field 68", new Proto3Message().setFieldSint6468(100));
  }

  @Test
  public void preserveUnknownFields() {
    Proto3Message expectedMessage = messageFactory().newMessage();
    Proto3Empty empty =
        ExperimentalSerializationUtil.fromByteArray(
            expectedMessage.toByteArray(), Proto3Empty.class);
    assertEquals(expectedMessage.getSerializedSize(), empty.getSerializedSize());
    assertEquals(expectedMessage.toByteString(), empty.toByteString());
  }

  @Test
  public void mapsShouldRoundtrip() throws IOException {
    roundtrip(
        "Proto3MessageWithMaps",
        new Proto3MessageFactory(2, 10, 2, 2).newMessageWithMaps(),
        Protobuf.getInstance().schemaFor(Proto3MessageWithMaps.class));
  }
}
