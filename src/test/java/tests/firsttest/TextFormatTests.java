package tests.firsttest;

import groovy.lang.GroovyShell;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alcereo.criteria.QueryBuilder;
import tests.firsttest.config.TestConfig;

import java.io.File;
import java.io.IOException;

/**
 * Created by alcereo on 01.05.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class TextFormatTests {

    @Autowired
    private QueryBuilder qBuilder;

    @BeforeClass
    public static void initDB() {

//        QueryBuilderTest.initData(factory);
    }


    @Test
    public void firstGroovyTest() throws IOException {

        GroovyShell shell = new GroovyShell();

        shell.setProperty("qBuilder", qBuilder);
        Object run = shell.parse(new File("./src/test/resources/firstGroovyTest.groovy")).run();

        System.out.println(run);

    }

}
