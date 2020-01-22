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

import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/**
 * A partial implementation of the {@link Parser} interface which implements as many methods of that
 * interface as possible in terms of other methods.
 *
 * <p>Note: This class implements all the convenience methods in the {@link Parser} interface. See
 * {@link Parser} for related javadocs. Subclasses need to implement {@link
 * Parser#parsePartialFrom(CodedInputStream)}
 *
 * @author liujisi@google.com (Pherl Liu)
 */
public abstract class AbstractParser<MessageType extends MessageLite>
    implements Parser<MessageType> {
  /** Creates an UninitializedMessageException for MessageType. */
  private UninitializedMessageException newUninitializedMessageException(MessageType message) {
    return new UninitializedMessageException(message);
  }

  /**
   * Helper method to check if message is initialized.
   *
   * @throws InvalidProtocolBufferException if it is not initialized.
   * @return The message to check.
   */
  private MessageType checkMessageInitialized(MessageType message)
      throws InvalidProtocolBufferException {
    if (message != null && !message.isInitialized()) {
      throw newUninitializedMessageException(message)
          .asInvalidProtocolBufferException()
          .setUnfinishedMessage(message);
    }
    return message;
  }

  @Override
  public MessageType parseFrom(CodedInputStream input)
      throws InvalidProtocolBufferException {
    return checkMessageInitialized(parsePartialFrom(input));
  }

  @Override
  public MessageType parsePartialFrom(ByteString data)
      throws InvalidProtocolBufferException {
    MessageType message;
    try {
      CodedInputStream input = data.newCodedInput();
      message = parsePartialFrom(input);
      try {
        input.checkLastTagWas(0);
      } catch (InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(message);
      }
      return message;
    } catch (InvalidProtocolBufferException e) {
      throw e;
    }
  }

  @Override
  public MessageType parseFrom(ByteString data)
      throws InvalidProtocolBufferException {
    return checkMessageInitialized(parsePartialFrom(data));
  }

  @Override
  public MessageType parseFrom(ByteBuffer data)
      throws InvalidProtocolBufferException {
    MessageType message;
    try {
      CodedInputStream input = CodedInputStream.newInstance(data);
      message = parsePartialFrom(input);
      try {
        input.checkLastTagWas(0);
      } catch (InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(message);
      }
    } catch (InvalidProtocolBufferException e) {
      throw e;
    }

    return checkMessageInitialized(message);
  }


  @Override
  public MessageType parsePartialFrom(
      byte[] data, int off, int len)
      throws InvalidProtocolBufferException {
    try {
      CodedInputStream input = CodedInputStream.newInstance(data, off, len);
      MessageType message = parsePartialFrom(input);
      try {
        input.checkLastTagWas(0);
      } catch (InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(message);
      }
      return message;
    } catch (InvalidProtocolBufferException e) {
      throw e;
    }
  }

  @Override
  public MessageType parsePartialFrom(byte[] data)
      throws InvalidProtocolBufferException {
    return parsePartialFrom(data, 0, data.length);
  }

  @Override
  public MessageType parseFrom(
      byte[] data, int off, int len)
      throws InvalidProtocolBufferException {
    return checkMessageInitialized(parsePartialFrom(data, off, len));
  }

  @Override
  public MessageType parseFrom(byte[] data)
      throws InvalidProtocolBufferException {
    return parseFrom(data, 0, data.length);
  }

  @Override
  public MessageType parsePartialFrom(InputStream input)
      throws InvalidProtocolBufferException {
    CodedInputStream codedInput = CodedInputStream.newInstance(input);
    MessageType message = parsePartialFrom(codedInput);
    try {
      codedInput.checkLastTagWas(0);
    } catch (InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(message);
    }
    return message;
  }

  @Override
  public MessageType parseFrom(InputStream input)
      throws InvalidProtocolBufferException {
    return checkMessageInitialized(parsePartialFrom(input));
  }

  @Override
  public MessageType parsePartialDelimitedFrom(
      InputStream input)
      throws InvalidProtocolBufferException {
    int size;
    try {
      int firstByte = input.read();
      if (firstByte == -1) {
        return null;
      }
      size = CodedInputStream.readRawVarint32(firstByte, input);
    } catch (IOException e) {
      throw new InvalidProtocolBufferException(e);
    }
    InputStream limitedInput = new LimitedInputStream(input, size);
    return parsePartialFrom(limitedInput);
  }

  /**
   * An InputStream implementations which reads from some other InputStream but is limited to a
   * particular number of bytes. Used by mergeDelimitedFrom(). This is intentionally
   * package-private so that UnknownFieldSet can share it.
   */
  static final class LimitedInputStream extends FilterInputStream {
    private int limit;

    LimitedInputStream(InputStream in, int limit) {
      super(in);
      this.limit = limit;
    }

    @Override
    public int available() throws IOException {
      return Math.min(super.available(), limit);
    }

    @Override
    public int read() throws IOException {
      if (limit <= 0) {
        return -1;
      }
      final int result = super.read();
      if (result >= 0) {
        --limit;
      }
      return result;
    }

    @Override
    public int read(final byte[] b, final int off, int len) throws IOException {
      if (limit <= 0) {
        return -1;
      }
      len = Math.min(len, limit);
      final int result = super.read(b, off, len);
      if (result >= 0) {
        limit -= result;
      }
      return result;
    }

    @Override
    public long skip(final long n) throws IOException {
      final long result = super.skip(Math.min(n, limit));
      if (result >= 0) {
        limit -= result;
      }
      return result;
    }
  }

  @Override
  public MessageType parseDelimitedFrom(InputStream input)
      throws InvalidProtocolBufferException {
    return checkMessageInitialized(parsePartialDelimitedFrom(input));
  }
}
