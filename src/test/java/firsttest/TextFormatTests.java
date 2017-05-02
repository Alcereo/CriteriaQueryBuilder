package firsttest;

import groovy.lang.GroovyShell;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.alcereo.criteria.QueryBuilder;

import java.io.File;
import java.io.IOException;

/**
 * Created by alcereo on 01.05.17.
 */
public class TextFormatTests {

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
    public void firstGroovyTest() throws IOException {

        GroovyShell shell = new GroovyShell();

        shell.setProperty("qBuilder", qBuilder);
        Object run = shell.parse(new File("./src/test/resources/firstGroovyTest.groovy")).run();

        System.out.println(run);

    }

}
