package firsttest;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.metamodel.internal.SingularAttributeImpl;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.alcereo.criteria.PredicateBuilder;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import java.util.Map;

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
                .addLink(ParametersEntity.class, PARAMS)
                .addFilter((cb, links) -> {
                    From params = links.get(PARAMS);

                    return cb.and(
                            params.get("id").in(1),
                            params.isNotNull()
                    );
                }).getResultList()
                .forEach(System.out::println);

    }

    @Test
    public void selectVersionsWithSomeFilters(){

        final String PARAMS = "params";

        PredicateBuilder
                .selectFrom(ProcessorsVersionsEntity.class)
                .addLink(CommandsEntity.class, PARAMS)
                .addFilter((cb, links) ->
                        cb.or(
                                cb.greaterThan(
                                        links.get(PARAMS).get("id"),
                                        3)
                                ,
                                links.get(PARAMS).get("id").in(1)
                                )
                )
                .getResultList()
                .forEach(System.out::println);

    }
}
