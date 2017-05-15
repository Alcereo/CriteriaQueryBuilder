package tests.firsttest;

import tests.firsttest.config.TestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alcereo.criteria.QueryBuilder;
import tests.entities.*;

import javax.persistence.criteria.From;
import java.util.*;

/**
 * Created by alcereo on 22.04.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryBuilderTest {

    @Autowired
    private QueryBuilder qBuilder;

    @Autowired
    private SessionFactory factory;

    @BeforeClass
    public static void initDB() {

//        initData(factory);

    }

    public static void initData(SessionFactory factory) {
        try (Session session = factory.openSession()) {

            ProcessorsVersionsEntity version;
            ProcessorsEntity proc;
            ParametersEntity param;
            CommandsEntity command;

            session.beginTransaction();

            CommandsEntity command1 = new CommandsEntity();
            command = command1;
            command.setId(1);
            command.setName("last error");
            session.saveOrUpdate(command);
            {

            }


            CommandsEntity command2 = new CommandsEntity();
            command = command2;
            command.setId(2);
            command.setName("pull cash");
            session.saveOrUpdate(command);

            {
                param = new ParametersEntity();
                param.setId(1);
                param.setName("count");
                param.setCommand(command);
                session.saveOrUpdate(param);
            }


            CommandsEntity command3 = new CommandsEntity();
            command = command3;
            command.setId(3);
            command.setName("count cash");
            session.saveOrUpdate(command);
            {

            }

            CommandsEntity command4 = new CommandsEntity();
            command = command4;
            command.setId(4);
            command.setName("shutdown");
            session.saveOrUpdate(command);
            {
                param = new ParametersEntity();
                param.setId(2);
                param.setName("latency");
                param.setCommand(command);
                session.saveOrUpdate(param);

                param = new ParametersEntity();
                param.setId(5);
                param.setName("admin");
                param.setCommand(command);
                session.saveOrUpdate(param);
            }

            CommandsEntity command5 = new CommandsEntity();
            command = command5;
            command.setId(5);
            command.setName("reboot");
            session.saveOrUpdate(command);
            {
                param = new ParametersEntity();
                param.setId(3);
                param.setName("latency");
                param.setCommand(command);
                session.saveOrUpdate(param);
            }


            CommandsEntity command6 = new CommandsEntity();
            command = command6;
            command.setId(6);
            command.setName("get status");
            session.saveOrUpdate(command);
            {
                param = new ParametersEntity();
                param.setId(4);
                param.setName("proc");
                param.setCommand(command);
                session.saveOrUpdate(param);
            }


//            VERSIONS
            version = new ProcessorsVersionsEntity();
            version.setId(1);
            version.setName("first version");
            session.saveOrUpdate(version);

            {
                proc = new ProcessorsEntity();
                proc.setId(2);
                proc.setName("cash in");
                proc.setProcessorVersion(version);
                {
                    proc.setCommands(new HashSet<>());
                    proc.getCommands().add(command1);
                    proc.getCommands().add(command2);
                    proc.getCommands().add(command3);
                }
                session.saveOrUpdate(proc);


                proc = new ProcessorsEntity();
                proc.setId(3);
                proc.setName("cash out");
                proc.setProcessorVersion(version);
                {
                    proc.setCommands(new HashSet<>());
                    proc.getCommands().add(command1);
                    proc.getCommands().add(command2);
                    proc.getCommands().add(command3);
                }
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
                {
                    proc.setCommands(new HashSet<>());
                    proc.getCommands().add(command1);
                }
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
                {
                    proc.setCommands(new HashSet<>());
                    proc.getCommands().add(command4);
                    proc.getCommands().add(command5);
                    proc.getCommands().add(command6);
                }
            }



            session.flush();

        };
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
                                links.get(COMMANDS).get("id").in(5)
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

    @Test
    public void selectParametersTest(){

        List<CommandsEntity> builderList = qBuilder
                .selectFrom(CommandsEntity.class)
                .addWhiteFilter((cb, links, root) ->
                        cb.greaterThanOrEqualTo(root.get("id"), 2)
                )
                .addBlackFilter((cb, links, root) -> root.in(5))
                .getResultList();

        List<CommandsEntity> trueList = factory
                .openSession()
                .byMultipleIds(CommandsEntity.class)
                .multiLoad(2, 3, 4, 6);

        Assert.assertEquals(
                builderList,
                trueList
        );

    }


}
