import com.example.tutorial.Person;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProtoLeoTest {
    @Test
    public void test() throws IOException {
        Person person = Person.newBuilder()
                .setId(1234)
                .build();

        ByteArrayOutputStream output = new ByteArrayOutputStream();
        person.writeTo(output);
    }
}
