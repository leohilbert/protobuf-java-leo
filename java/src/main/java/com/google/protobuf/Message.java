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
import java.util.Map;

/**
 * Abstract interface implemented by Protocol Message objects.
 *
 * <p>See also {@link MessageLite}, which defines most of the methods that typical users care about.
 * {@link Message} adds to it methods that are not available in the "lite" runtime. The biggest
 * added features are introspection and reflection -- i.e., getting descriptors for the message type
 * and accessing the field values dynamically.
 *
 * @author kenton@google.com Kenton Varda
 */
public interface Message extends MessageLite, MessageOrBuilder {

  // (From MessageLite, re-declared here only for return type covariance.)
  @Override
  Parser<? extends Message> getParserForType();


  // -----------------------------------------------------------------
  // Comparison and hashing

  /**
   * Compares the specified object with this message for equality. Returns {@code true} if the given
   * object is a message of the same type (as defined by {@code getDescriptorForType()}) and has
   * identical values for all of its fields. Subclasses must implement this; inheriting {@code
   * Object.equals()} is incorrect.
   *
   * @param other object to be compared for equality with this message
   * @return {@code true} if the specified object is equal to this message
   */
  @Override
  boolean equals(Object other);

  /**
   * Returns the hash code value for this message. The hash code of a message should mix the
   * message's type (object identity of the descriptor) with its contents (known and unknown field
   * values). Subclasses must implement this; inheriting {@code Object.hashCode()} is incorrect.
   *
   * @return the hash code value for this message
   * @see Map#hashCode()
   */
  @Override
  int hashCode();
}
