import static com.google.protobuf.ExtensionRegistryLite.getEmptyRegistry;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.custom.CustomOwnerClass;
import com.example.custom.CustomPhoneType;
import com.example.tutorial.AddressBook;
import com.example.tutorial.Person;
import com.example.tutorial.PhoneType;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.MessageLite;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.UUID;

public class ProtoLeoTest {
    @Test
    public void test() throws IOException {
        UUID TEST_UUID1 = UUID.randomUUID();
        UUID TEST_UUID2 = UUID.randomUUID();
        Person person = new Person()
                .setId(TEST_UUID1)
                .setEmail("hans@wurst.de")
                .addPhones(new Person.PhoneNumber().setNumber("12345").setType(CustomPhoneType.HOME))
                .addFriendIds("Dieter").addFriendIds("Horst")
                .addFavoriteNumber(14).addFavoriteNumber(15);
        assertEquals(person.getId(), TEST_UUID1);
        assertEquals(person.getEmail(), "hans@wurst.de");
        assertThat(person.toString()).isNotNull();

        final Person deserPerson = new Person(CodedInputStream.newInstance(getByteArray(person)), getEmptyRegistry());
        assertEquals(deserPerson.getId(), TEST_UUID1);
        assertEquals(deserPerson.getEmail(), "hans@wurst.de");
        assertEquals(deserPerson, person);

        person.setId(TEST_UUID2);
        assertEquals(person.getId(), TEST_UUID2);
        assertThat(deserPerson).isNotEqualTo(person);

        deserPerson.setEmail("horst@wurst.de");
        assertEquals(deserPerson.getEmail(), "horst@wurst.de");

        person.updateFrom(CodedInputStream.newInstance(getByteArray(deserPerson)), getEmptyRegistry());
        assertEquals(person.getEmail(), "horst@wurst.de");
        assertEquals(person.getId(), TEST_UUID1);
        assertThat(person.getFriendIdsList()).containsExactly("Dieter", "Horst");
        assertThat(person.getFavoriteNumberList()).containsExactly(14, 15);

        AddressBook addressBook = new AddressBook()
                .addPeople(person)
                .setOwner(new CustomOwnerClass("owner@test.de"));
        assertThat(addressBook.toString()).isNotNull();

        final AddressBook deserAddressbook = new AddressBook(CodedInputStream.newInstance(getByteArray(addressBook)), getEmptyRegistry());
        assertThat(deserAddressbook.getPeopleList()).containsExactly(person);
        assertThat(deserAddressbook.getOwner().email).isEqualTo("owner@test.de");
    }

    private byte[] getByteArray(final MessageLite message) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        message.writeTo(output);
        return output.toByteArray();
    }
}
