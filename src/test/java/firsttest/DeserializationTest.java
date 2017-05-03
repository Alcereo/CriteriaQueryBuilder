package firsttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import ru.alcereo.usability.annotations.UMetaClass;
import ru.alcereo.usability.annotations.UPredicateView;
import ru.alcereo.usability.predicates.UPredicate;

import java.io.File;
import java.io.IOException;

import static org.reflections.ReflectionUtils.*;

/**
 * Created by alcereo on 02.05.17.
 */
public class DeserializationTest {

    static final String FILE1 = "./src/test/resources/testMessage.json";

    @Test
    public void first() throws IOException {

        UPredicate predicate = new ObjectMapper().readValue(new File(FILE1), UPredicate.class);

        System.out.println(""+predicate);

        new ObjectMapper().readValue(new File(FILE1), UPredicate.class);

    }

}
