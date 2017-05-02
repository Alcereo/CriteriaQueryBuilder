package firsttest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import ru.alcereo.usability.predicates.EqualPredictive;
import ru.alcereo.usability.predicates.UPredicate;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Scanner;

/**
 * Created by alcereo on 02.05.17.
 */
public class DeserializationTest {

    static final String FILE1 = "./src/test/resources/testMessage.json";

    @Test
    public void first() throws IOException {

        UPredicate predicate = new ObjectMapper().readValue(new File(FILE1), UPredicate.class);

        System.out.println(""+predicate);

    }

}
