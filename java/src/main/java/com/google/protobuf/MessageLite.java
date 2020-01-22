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

// TODO(kenton):  Use generics?  E.g. Builder<BuilderType extends Builder>, then
//   mergeFrom*() could return BuilderType for better type-safety.

package com.google.protobuf;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Abstract interface implemented by Protocol Message objects.
 *
 * <p>This interface is implemented by all protocol message objects. Non-lite messages additionally
 * implement the Message interface, which is a subclass of MessageLite. Use MessageLite instead when
 * you only need the subset of features which it supports -- namely, nothing that uses descriptors
 * or reflection. You can instruct the protocol compiler to generate classes which implement only
 * MessageLite, not the full Message interface, by adding the follow line to the .proto file:
 *
 * <pre>
 *   option optimize_for = LITE_RUNTIME;
 * </pre>
 *
 * <p>This is particularly useful on resource-constrained systems where the full protocol buffers
 * runtime library is too big.
 *
 * <p>Note that on non-constrained systems (e.g. servers) when you need to link in lots of protocol
 * definitions, a better way to reduce total code footprint is to use {@code optimize_for =
 * CODE_SIZE}. This will make the generated code smaller while still supporting all the same
 * features (at the expense of speed). {@code optimize_for = LITE_RUNTIME} is best when you only
 * have a small number of message types linked into your binary, in which case the size of the
 * protocol buffers runtime itself is the biggest problem.
 *
 * @author kenton@google.com Kenton Varda
 */
public interface MessageLite extends MessageLiteOrBuilder {

  /**
   * Serializes the message and writes it to {@code output}. This does not flush or close the
   * stream.
   */
  void writeTo(CodedOutputStream output) throws IOException;

  /**
   * Get the number of bytes required to encode this message. The result is only computed on the
   * first call and memoized after that.
   */
  int getSerializedSize();

  /** Gets the parser for a message of the same type as this message. */
  Parser<? extends MessageLite> getParserForType();

  // -----------------------------------------------------------------
  // Convenience methods.

  /**
   * Serializes the message to a {@code ByteString} and returns it. This is just a trivial wrapper
   * around {@link #writeTo(CodedOutputStream)}.
   */
  ByteString toByteString();

  /**
   * Serializes the message to a {@code byte} array and returns it. This is just a trivial wrapper
   * around {@link #writeTo(CodedOutputStream)}.
   */
  byte[] toByteArray();

  /**
   * Serializes the message and writes it to {@code output}. This is just a trivial wrapper around
   * {@link #writeTo(CodedOutputStream)}. This does not flush or close the stream.
   *
   * <p>NOTE: Protocol Buffers are not self-delimiting. Therefore, if you write any more data to the
   * stream after the message, you must somehow ensure that the parser on the receiving end does not
   * interpret this as being part of the protocol message. This can be done e.g. by writing the size
   * of the message before the data, then making sure to limit the input to that size on the
   * receiving end (e.g. by wrapping the InputStream in one which limits the input). Alternatively,
   * just use {@link #writeDelimitedTo(OutputStream)}.
   */
  void writeTo(OutputStream output) throws IOException;

  /**
   * Like {@link #writeTo(OutputStream)}, but writes the size of the message as a varint before
   * writing the data. This allows more data to be written to the stream after the message without
   * the need to delimit the message data yourself. Use {@link
   * Builder#mergeDelimitedFrom(InputStream)} (or the static method {@code
   * YourMessageType.parseDelimitedFrom(InputStream)}) to parse messages written by this method.
   */
  void writeDelimitedTo(OutputStream output) throws IOException;

}
