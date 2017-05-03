package firsttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Predicate;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;
import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.usability.annotations.UMetaClass;
import ru.alcereo.usability.annotations.UPredicateView;
import ru.alcereo.usability.meta.Commands_;
import ru.alcereo.usability.meta.ProcessorsVersions_;
import ru.alcereo.usability.predicates.UPredicate;

import java.io.File;
import java.io.IOException;

import static org.reflections.ReflectionUtils.*;

/**
 * Created by alcereo on 02.05.17.
 */
public class DeserializationTest {

    static final String FILE1 = "./src/test/resources/testMessage.json";

    private static SessionFactory factory;
    private static QueryBuilder qBuilder;

    @BeforeClass
    public static void initDB() {
        factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();

//        QueryBuilderTest.initData(factory);

        qBuilder = new QueryBuilder();
        qBuilder.setFactory(factory);
    }

    @Test
    public void first() throws IOException {

        UPredicate predicate = new ObjectMapper().readValue(new File(FILE1), UPredicate.class);

        System.out.println(""+predicate);

        ProcessorsVersions_
//                TODO: qBuilder - будет инжектиться
                .select(qBuilder)
                .where(predicate)
                .getResultList()
                .forEach(System.out::println);

    }

}
