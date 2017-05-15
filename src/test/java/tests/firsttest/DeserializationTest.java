package tests.firsttest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alcereo.criteria.QueryBuilder;
import tests.entities.meta.ProcessorsVersions_;
import ru.alcereo.usability.predicates.UPredicate;
import tests.firsttest.config.TestConfig;

import java.io.File;
import java.io.IOException;

/**
 * Created by alcereo on 02.05.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class DeserializationTest {

    static final String FILE1 = "./src/test/resources/testMessage.json";

    @Autowired
    private QueryBuilder qBuilder;

    @BeforeClass
    public static void initDB() {

//        QueryBuilderTest.initData(factory);
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
