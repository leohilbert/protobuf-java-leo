import static com.google.protobuf.ExtensionRegistryLite.getEmptyRegistry;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.example.tutorial.Person;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.MessageLite;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProtoLeoTest {
    @Test
    public void test() throws IOException {
        Person person = Person.newBuilder().setId(1234).setEmail("hans@wurst.de").build();
        assertEquals(person.getId(), 1234);
        assertEquals(person.getEmail(), "hans@wurst.de");

        final Person person2 = new Person(CodedInputStream.newInstance(getByteArray(person)), getEmptyRegistry());
        assertEquals(person2.getId(), 1234);
        assertEquals(person2.getEmail(), "hans@wurst.de");

        person.setId(2345);
        assertEquals(person.getId(), 2345);

        person2.setEmail("horst@wurst.de");
        assertEquals(person2.getEmail(), "horst@wurst.de");

        person.updateFrom(CodedInputStream.newInstance(getByteArray(person2)), getEmptyRegistry());
        assertEquals(person.getEmail(), "horst@wurst.de");
        assertEquals(person.getId(), 1234);
    }

    private byte[] getByteArray(final MessageLite message) throws IOException {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        message.writeTo(output);
        return output.toByteArray();
    }
}
