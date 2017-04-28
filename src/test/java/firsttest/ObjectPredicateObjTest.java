package firsttest;

import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.usability.*;
import ru.alcereo.usability.meta.Parameters_;

import javax.persistence.metamodel.*;

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

        UPredicate mainPredicate;

        AndUPredicate and = new AndUPredicate();
        mainPredicate = and;

        OrUPredicate or = new OrUPredicate();
        and.getChildPredicates().add(or);

        EqualPredictive<Integer> equalPredictive = new EqualPredictive<>();
        equalPredictive.setAttributive(parameters_id);
        equalPredictive.setSubject(1);
        or.getChildPredicates().add(equalPredictive);

        GreaterThanPredictive<Integer> greaterThanPredictive = new GreaterThanPredictive<>();
        greaterThanPredictive.setAttributive(parameters_id);
        greaterThanPredictive.setSubject(3);
        or.getChildPredicates().add(greaterThanPredictive);

        InPredictive<String> inPredictive = new InPredictive<>();
        inPredictive.setAttributive(parameters_name);
        inPredictive.setSubjects("latency", "count");
        and.getChildPredicates().add(inPredictive);


        //

        System.out.println("!!!+");
        mainPredicate.getLinks().forEach(System.out::println);
        System.out.println("!!!-");

        //

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

    @Test
    public void testToConstruct(){

        final String PARAMETERS = ParametersEntity.class.getName();

//        Attributive<?,ParametersEntity> parametersTable = new Attributive<>();
//        parametersTable.setViewName("PARAMETERS_LINK");
//
//        Attributive<ParametersEntity,Integer> parameters_id = new Attributive<>();
//        parameters_id.setParent(parametersTable);
//        parameters_id.setAttribute(
//                (SingularAttribute<ParametersEntity, Integer>)
//                        factory.getMetamodel()
//                                .entity(ParametersEntity.class)
//                                .getSingularAttribute("id", Integer.class)
//        );
//
//        Attributive<ParametersEntity,String> parameters_name = new Attributive<>();
//        parameters_name.setParent(parametersTable);
//        parameters_name.setAttribute(
//                (SingularAttribute<ParametersEntity, String>)
//                        factory.getMetamodel()
//                                .entity(ParametersEntity.class)
//                                .getSingularAttribute("name", String.class)
//        );

//        mainPredicate =
//                parameters_name.in("latency", "count")
//                        .and(parameters_id.equal(1)
//                                        .or(parameters_id.greaterThan(3))
//                                        .or(parameters_id.greaterThan(4))
//                        ).and(parameters_name.equal("Alcereo")
//                );

        //

//        Attribute constaruct

        UPredicate mainPredicate;

        mainPredicate =
                Parameters_.name().in("latency", "count")
                        .and(Parameters_.id().equal(1)
                                        .or(Parameters_.id().greaterThan(3))
                                        .or(Parameters_.id().greaterThan(4))
                        ).and(Parameters_.name().equal("Alcereo")
                );

//

        System.out.println("!!!+");
        mainPredicate.getLinks().forEach(System.out::println);
        System.out.println("!!!-");

        //

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
