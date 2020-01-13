// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: addressbook.proto

package com.example.tutorial;

/**
 * Protobuf type {@code tutorial.AddressBook}
 */
public  final class AddressBook extends
    de.leohilbert.proto.GeneratedMessageLeo implements
    // @@protoc_insertion_point(message_implements:tutorial.AddressBook)
    AddressBookInterface<AddressBook> {
private static final long serialVersionUID = 0L;
  public AddressBook() {
    people_ = new java.util.ArrayList<com.example.tutorial.Person>();
    owner_ = null;
    afterMessageInit();
  }

  @java.lang.Override
  @SuppressWarnings({"unused"})
  protected java.lang.Object newInstance(
      UnusedPrivateParameter unused) {
    return new AddressBook();
  }

  @java.lang.Override
  public final com.google.protobuf.UnknownFieldSet
  getUnknownFields() {
    return this.unknownFields;
  }
  public AddressBook(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    this();
    updateFrom(input, extensionRegistry);
  }
  public void updateFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    if (extensionRegistry == null) {
      throw new java.lang.NullPointerException();
    }
    int mutable_bitField0_ = 0;
    com.google.protobuf.UnknownFieldSet.Builder unknownFields =
        com.google.protobuf.UnknownFieldSet.newBuilder();
    try {
      boolean done = false;
      while (!done) {
        int tag = input.readTag();
        switch (tag) {
          case 0:
            done = true;
            break;
          case 10: {
            if (!((mutable_bitField0_ & 0x00000001) != 0)) {
              people_ = new java.util.ArrayList<com.example.tutorial.Person>();
              mutable_bitField0_ |= 0x00000001;
            }
            people_.add(
                input.readMessage(com.example.tutorial.Person.parser(), extensionRegistry));
            break;
          }
          case 18: {

            owner_ = de.leohilbert.protoconverter.ProtoConverter_COM_EXAMPLE_CUSTOM_CUSTOMOWNERCLASS.fromProto(input.readMessage(com.example.tutorial.Person.parser(), extensionRegistry));
            break;
          }
          default: {
            if (!parseUnknownField(
                input, unknownFields, extensionRegistry, tag)) {
              done = true;
            }
            break;
          }
        }
      }
      afterMessageInit();
    } catch (com.google.protobuf.InvalidProtocolBufferException e) {
      throw e.setUnfinishedMessage(this);
    } catch (java.io.IOException e) {
      throw new com.google.protobuf.InvalidProtocolBufferException(
          e).setUnfinishedMessage(this);
    } finally {
      if (((mutable_bitField0_ & 0x00000001) != 0)) {
        people_ = java.util.Collections.unmodifiableList(people_);
      }
      this.unknownFields = unknownFields.build();
      makeExtensionsImmutable();
    }
  }
  public static final com.google.protobuf.Descriptors.Descriptor
      getDescriptor() {
    return com.example.tutorial.AddressBookProtos.internal_static_tutorial_AddressBook_descriptor;
  }

  @java.lang.Override
  protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internalGetFieldAccessorTable() {
    return com.example.tutorial.AddressBookProtos.internal_static_tutorial_AddressBook_fieldAccessorTable
        .ensureFieldAccessorsInitialized(
            com.example.tutorial.AddressBook.class);
  }

  private java.util.List<com.example.tutorial.Person> people_;
  /**
   * <code>repeated .tutorial.Person people = 1[json_name = "people"];</code>
   */
  public java.util.List<com.example.tutorial.Person> getPeopleList() {
    return people_;
  }
  /**
   * <code>repeated .tutorial.Person people = 1[json_name = "people"];</code>
   */
  public java.util.List<? extends com.example.tutorial.PersonInterface> 
      getPeopleInterfaceList() {
    return people_;
  }
  /**
   * <code>repeated .tutorial.Person people = 1[json_name = "people"];</code>
   */
  public int getPeopleCount() {
    return people_.size();
  }
  /**
   * <code>repeated .tutorial.Person people = 1[json_name = "people"];</code>
   */
  public com.example.tutorial.Person getPeople(int index) {
    return people_.get(index);
  }
  /**
   * <code>repeated .tutorial.Person people = 1[json_name = "people"];</code>
   * @param index The index to set the value at.
   * @param value The people to set.
   * @return 'This' for chaining.
   */
  public AddressBook setPeople(
      int index, com.example.tutorial.Person value) {
    people_.set(index, value);
    onChanged(PEOPLE_FIELD_NUMBER);
    return this;
  }
  /**
   * <code>repeated .tutorial.Person people = 1[json_name = "people"];</code>
   * @param value The people to add.
   * @return 'This' for chaining.
   */
  public AddressBook addPeople(
      com.example.tutorial.Person value) {
    people_.add(value);
    onChanged(PEOPLE_FIELD_NUMBER);
    return this;
  }
  /**
   * <code>repeated .tutorial.Person people = 1[json_name = "people"];</code>
   * @param values The people to add.
   * @return 'This' for chaining.
   */
  public AddressBook addAllPeople(
      java.util.Collection<com.example.tutorial.Person> values) {
    people_.addAll(values);
    onChanged(PEOPLE_FIELD_NUMBER);
    return this;
  }
  /**
   * <code>repeated .tutorial.Person people = 1[json_name = "people"];</code>
   * @return 'This' for chaining.
   */
  public AddressBook clearPeople() {
    people_ = new java.util.ArrayList<com.example.tutorial.Person>(people_);
    onChanged(PEOPLE_FIELD_NUMBER);
    return this;
  }

  private com.example.custom.CustomOwnerClass owner_;
  /**
   * <code>.tutorial.Person owner = 2[json_name = "owner", (.leo.proto.javatype) = "com.example.custom.CustomOwnerClass"];</code>
   * @return The owner.
   */
  public com.example.custom.CustomOwnerClass getOwner() {
    return owner_;
  }
  /**
   * <code>.tutorial.Person owner = 2[json_name = "owner", (.leo.proto.javatype) = "com.example.custom.CustomOwnerClass"];</code>
   * @param value The owner to set.
   */
  public AddressBook setOwner(com.example.custom.CustomOwnerClass value) {
    if(owner_ != value) {    
      owner_ = value;
      onChanged(OWNER_FIELD_NUMBER);
    }
    return this;
  }
  /**
   * <code>.tutorial.Person owner = 2[json_name = "owner", (.leo.proto.javatype) = "com.example.custom.CustomOwnerClass"];</code>
   * @return 'This' for chaining.
   */
  public AddressBook clearOwner() {
    owner_ = null;
    onChanged(OWNER_FIELD_NUMBER);
    return this;
  }

  private byte memoizedIsInitialized = -1;
  @java.lang.Override
  public final boolean isInitialized() {
    byte isInitialized = memoizedIsInitialized;
    if (isInitialized == 1) return true;
    if (isInitialized == 0) return false;

    memoizedIsInitialized = 1;
    return true;
  }

  @java.lang.Override
  public void writeTo(com.google.protobuf.CodedOutputStream output)
                      throws java.io.IOException {
    for (int i = 0; i < people_.size(); i++) {
      output.writeMessage(1, people_.get(i));
    }
    if (owner_ != null) {
      output.writeMessage(2, de.leohilbert.protoconverter.ProtoConverter_COM_EXAMPLE_CUSTOM_CUSTOMOWNERCLASS.toProto(owner_));
    }
    unknownFields.writeTo(output);
  }

  @java.lang.Override
  public int getSerializedSize() {
    int size = memoizedSize;
    if (size != -1) return size;

    size = 0;
    for (int i = 0; i < people_.size(); i++) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(1, people_.get(i));
    }
    if (owner_ != null) {
      size += com.google.protobuf.CodedOutputStream
        .computeMessageSize(2, de.leohilbert.protoconverter.ProtoConverter_COM_EXAMPLE_CUSTOM_CUSTOMOWNERCLASS.toProto(owner_));
    }
    size += unknownFields.getSerializedSize();
    memoizedSize = size;
    return size;
  }

  @java.lang.Override
  public boolean equals(final java.lang.Object obj) {
    if (obj == this) {
     return true;
    }
    if (!(obj instanceof com.example.tutorial.AddressBook)) {
      return false;
    }
    com.example.tutorial.AddressBook other = (com.example.tutorial.AddressBook) obj;

    if (!java.util.Objects.equals(getPeopleList(),
        other.getPeopleList())) return false;
    if (!java.util.Objects.equals(getOwner(),
        other.getOwner())) return false;
    if (!unknownFields.equals(other.unknownFields)) return false;
    return true;
  }

  @java.lang.Override
  public int hashCode() {
    if (memoizedHashCode != 0) {
      return memoizedHashCode;
    }
    int hash = 41;
    hash = (19 * hash) + getDescriptor().hashCode();
    if (getPeopleCount() > 0) {
      hash = (37 * hash) + PEOPLE_FIELD_NUMBER;
      hash = (53 * hash) + getPeopleList().hashCode();
    }
    hash = (37 * hash) + OWNER_FIELD_NUMBER;
    hash = (53 * hash) + getOwner().hashCode();
    hash = (29 * hash) + unknownFields.hashCode();
    memoizedHashCode = hash;
    return hash;
  }

  public static com.example.tutorial.AddressBook parseFrom(
      java.nio.ByteBuffer data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.tutorial.AddressBook parseFrom(
      java.nio.ByteBuffer data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.tutorial.AddressBook parseFrom(
      com.google.protobuf.ByteString data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.tutorial.AddressBook parseFrom(
      com.google.protobuf.ByteString data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.tutorial.AddressBook parseFrom(byte[] data)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data);
  }
  public static com.example.tutorial.AddressBook parseFrom(
      byte[] data,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws com.google.protobuf.InvalidProtocolBufferException {
    return PARSER.parseFrom(data, extensionRegistry);
  }
  public static com.example.tutorial.AddressBook parseFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.example.tutorial.AddressBook parseFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.example.tutorial.AddressBook parseDelimitedFrom(java.io.InputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input);
  }
  public static com.example.tutorial.AddressBook parseDelimitedFrom(
      java.io.InputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
  }
  public static com.example.tutorial.AddressBook parseFrom(
      com.google.protobuf.CodedInputStream input)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input);
  }
  public static com.example.tutorial.AddressBook parseFrom(
      com.google.protobuf.CodedInputStream input,
      com.google.protobuf.ExtensionRegistryLite extensionRegistry)
      throws java.io.IOException {
    return com.google.protobuf.GeneratedMessageV3
        .parseWithIOException(PARSER, input, extensionRegistry);
  }


  // @@protoc_insertion_point(class_scope:tutorial.AddressBook)
  private static final com.example.tutorial.AddressBook DEFAULT_INSTANCE;
  static {
    DEFAULT_INSTANCE = new com.example.tutorial.AddressBook();
  }

  public static com.example.tutorial.AddressBook getDefaultInstance() {
    return DEFAULT_INSTANCE;
  }

  private static final com.google.protobuf.Parser<AddressBook>
      PARSER = new com.google.protobuf.AbstractParser<AddressBook>() {
    @java.lang.Override
    public AddressBook parsePartialFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return new AddressBook(input, extensionRegistry);
    }
  };

  public static com.google.protobuf.Parser<AddressBook> parser() {
    return PARSER;
  }

  @java.lang.Override
  public com.google.protobuf.Parser<AddressBook> getParserForType() {
    return PARSER;
  }

  @java.lang.Override
  public com.example.tutorial.AddressBook getDefaultInstanceForType() {
    return DEFAULT_INSTANCE;
  }

}

