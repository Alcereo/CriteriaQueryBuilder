package firsttest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.entities.ProcessorsEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;

import javax.persistence.criteria.From;

/**
 * Created by alcereo on 22.04.17.
 */
public class QueryBuilderTest {

    private static SessionFactory factory;
    private static QueryBuilder qBuilder;

    @BeforeClass
    public static void initFactory() {
        factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();

//        initDAta
        try (Session session = factory.openSession()) {

            ProcessorsVersionsEntity version;
            ProcessorsEntity proc;

            session.beginTransaction();

            version = new ProcessorsVersionsEntity();
            version.setId(1);
            version.setName("first version");
            session.saveOrUpdate(version);

            {
                proc = new ProcessorsEntity();
                proc.setId(2);
                proc.setName("cash in");
                proc.setProcessorVersion(version);
                session.saveOrUpdate(proc);

                proc = new ProcessorsEntity();
                proc.setId(3);
                proc.setName("cash out");
                proc.setProcessorVersion(version);
                session.saveOrUpdate(proc);
            }


            version = new ProcessorsVersionsEntity();
            version.setId(2);
            version.setName("second version");
            session.saveOrUpdate(version);

            {
                proc = new ProcessorsEntity();
                proc.setId(4);
                proc.setName("monitor");
                proc.setProcessorVersion(version);
                session.saveOrUpdate(proc);
            }

            version = new ProcessorsVersionsEntity();
            version.setId(3);
            version.setName("deprecated");
            session.saveOrUpdate(version);

            {
                proc = new ProcessorsEntity();
                proc.setId(5);
                proc.setName("admin");
                proc.setProcessorVersion(version);
                session.saveOrUpdate(proc);
            }



            session.flush();

        };


        qBuilder = new QueryBuilder();
        qBuilder.setFactory(factory);
    }

    @Test
    public void selectVersions() {

        final String PARAMS = "params";

        qBuilder
                .selectFrom(ProcessorsVersionsEntity.class)
                .addWhiteLink(ParametersEntity.class, PARAMS)
                .addWhiteFilter((cb, links, root) -> {
                    From params = links.get(PARAMS);

                    return cb.or(
                            params.get("id").in(1),
                            params.isNull()
                    );
                }).getResultList()
                .forEach(System.out::println);

    }

    @Test
    public void selectVersionsWithSomeFilters() {

        final String PARAMETERS = "PARAMETERS_LINK";

        String COMMANDS = "commands";
        qBuilder
                .selectFrom(ProcessorsVersionsEntity.class)
                .addWhiteLink(ParametersEntity.class, PARAMETERS)
                .addWhiteFilter(
                        (cb, links, root) ->
                                cb.or(
                                        links.get(PARAMETERS)
                                                .get("id")
                                                .in(1)
                                        ,
                                        links.get(PARAMETERS)
                                                .isNull()
                                )
                )
                .addBlackLink(CommandsEntity.class, COMMANDS)
                .addBlackFilter(
                        (cb, links, root) ->
                                links.get(COMMANDS).get("id").in(2)
                )
                .getResultList()
                .forEach(System.out::println);
    }

    @Test
    public void selectCommandsPaginationTest() {

        qBuilder
                .selectFrom(CommandsEntity.class)
                .addWhiteFilter(
                        (cb, links, root) -> cb.greaterThan(root.get("id"), 0)
                )
                .addOrder((cb, root) -> cb.asc(root.get("name")))
                .setPagination(2, 3)
                .getResultList()
                .forEach(System.out::println);

    }
}
