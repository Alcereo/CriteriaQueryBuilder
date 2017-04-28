package firsttest;

import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.usability.*;

import javax.persistence.metamodel.*;
import java.lang.reflect.Member;

/**
 * Created by alcereo on 27.04.17.
 */
public class ObjectPredicateObjTest {

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
    public void first(){

        final String PARAMETERS = "PARAMETERS_LINK";

        Attributive<?,ParametersEntity> parametersTable = new Attributive<>();
        parametersTable.setViewName("PARAMETERS_LINK");

        Attributive<ParametersEntity,Integer> parameters_id = new Attributive<>();
        parameters_id.setParent(parametersTable);
        parameters_id.setAttribute(
                (SingularAttribute<ParametersEntity, Integer>)
                        factory.getMetamodel()
                                .entity(ParametersEntity.class)
                                .getSingularAttribute("id", Integer.class)
        );

        Attributive<ParametersEntity,String> parameters_name = new Attributive<>();
        parameters_name.setParent(parametersTable);
        parameters_name.setAttribute(
                (SingularAttribute<ParametersEntity, String>)
                        factory.getMetamodel()
                                .entity(ParametersEntity.class)
                                .getSingularAttribute("name", String.class)
        );

        Predicate_Obj mainPredicate;

        AndPredicateObj and = new AndPredicateObj();
        mainPredicate = and;

        OrPredicateObj or = new OrPredicateObj();
        and.getPredicateObjs().add(or);

        EqualPredictive<Integer> equalPredictive = new EqualPredictive<>();
        equalPredictive.setAttributive(parameters_id);
        equalPredictive.setObject(1);
        or.getPredicateObjs().add(equalPredictive);

        GreaterThanPredictive<Integer> greaterThanPredictive = new GreaterThanPredictive<>();
        greaterThanPredictive.setAttributive(parameters_id);
        greaterThanPredictive.setObject(3);
        or.getPredicateObjs().add(greaterThanPredictive);

        InPredictive<String> inPredictive = new InPredictive<>();
        inPredictive.setAttributive(parameters_name);
        inPredictive.setSubjects("latency", "count");
        and.getPredicateObjs().add(inPredictive);


//

        System.out.println("!!!+");
        mainPredicate.getLinks().forEach(System.out::println);
        System.out.println("!!!-");

        qBuilder
                .selectFrom(ProcessorsVersionsEntity.class)
                .addWhiteLink(ParametersEntity.class, PARAMETERS)
                .addWhiteFilter(
                        (cb, links, root) ->
                                cb.and(
                                        cb.or(
                                                cb.equal(
                                                        links.get(PARAMETERS).get("id"),
                                                        1
                                                ),
                                                cb.greaterThan(
                                                        links.get(PARAMETERS).get("id"),
                                                        3
                                                )
                                        ),
                                        cb.in(links.get(PARAMETERS).get("name"))
                                                .value("latency")
                                                .value("count")
                                )
                )
                .getResultList()
                .forEach(System.out::println);


        qBuilder
                .selectFrom(ProcessorsVersionsEntity.class)
                .addWhiteLink(ParametersEntity.class, PARAMETERS)
                .addWhiteFilter(
                        (cb, links, root) ->
                                mainPredicate.buildCriteriaPredicate(
                                        new CriteriaBuildData(
                                                links,
                                                cb
                                        )
                                )
                )
                .getResultList()
                .forEach(System.out::println);

    }

}
