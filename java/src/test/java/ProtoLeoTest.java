import static com.google.protobuf.ExtensionRegistryLite.getEmptyRegistry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.tutorial.Person;
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
        Person person = Person.newBuilder().setId(TEST_UUID1).setEmail("hans@wurst.de").build();
        assertEquals(person.getId(), TEST_UUID1);
        assertEquals(person.getEmail(), "hans@wurst.de");

        final Person person2 = new Person(CodedInputStream.newInstance(getByteArray(person)), getEmptyRegistry());
        assertEquals(person2.getId(), TEST_UUID1);
        assertEquals(person2.getEmail(), "hans@wurst.de");

        person.setId(TEST_UUID2);
        assertEquals(person.getId(), TEST_UUID2);

        person2.setEmail("horst@wurst.de");
        assertEquals(person2.getEmail(), "horst@wurst.de");

        person.updateFrom(CodedInputStream.newInstance(getByteArray(person2)), getEmptyRegistry());
        assertEquals(person.getEmail(), "horst@wurst.de");
        assertEquals(person.getId(), TEST_UUID1);
    }

    private byte[] getByteArray(final MessageLite message) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        message.writeTo(output);
        return output.toByteArray();
    }
}
