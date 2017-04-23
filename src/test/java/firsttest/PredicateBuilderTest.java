package firsttest;

import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.alcereo.criteria.PredicateBuilder;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;

import javax.persistence.criteria.From;

/**
 * Created by alcereo on 22.04.17.
 */
public class PredicateBuilderTest {

    private static SessionFactory factory;

    @BeforeClass
    public static void initFactory() {
        factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        PredicateBuilder.setFactory(factory);
    }


    @Test
    public void selectVersions(){

        final String PARAMS = "params";

        PredicateBuilder
                .selectFrom(ProcessorsVersionsEntity.class)
                .addWhiteLink(ParametersEntity.class, PARAMS)
                .addWhiteFilter((cb, links) -> {
                    From params = links.get(PARAMS);

                    return cb.or(
                            params.get("id").in(1),
                            params.isNull()
                    );
                }).getResultList()
                .forEach(System.out::println);

    }

    @Test
    public void selectVersionsWithSomeFilters(){

        final String PARAMETERS = "PARAMETERS_LINK";

        String COMMANDS = "commands";
        PredicateBuilder
                .selectFrom(ProcessorsVersionsEntity.class)
                .addWhiteLink(ParametersEntity.class, PARAMETERS)
                .addWhiteFilter(
                        (cb, links) ->
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
                        (cb, links) ->
                                links.get(COMMANDS).get("id").in(2)
                )
                .getResultList()
                .forEach(System.out::println);
    }
}
