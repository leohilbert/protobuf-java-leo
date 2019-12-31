package de.leohilbert.protoconverter;

import com.example.custom.CustomOwnerClass;
import com.example.tutorial.Person;

public class ProtoConverter_COM_EXAMPLE_CUSTOM_CUSTOMOWNERCLASS {
    public static CustomOwnerClass fromProto(final Person obj) {
        return new CustomOwnerClass(obj.getEmail());
    }

    public static Person toProto(final CustomOwnerClass obj) {
        return new Person().setEmail(obj.email);
    }
}

