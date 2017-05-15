package tests.firsttest;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alcereo.criteria.QueryBuilder;
import tests.entities.ParametersEntity;
import tests.entities.ProcessorsVersionsEntity;
import ru.alcereo.futils.Function2;
import ru.alcereo.usability.*;
import tests.entities.meta.Commands_;
import tests.entities.meta.Parameters_;
import tests.entities.meta.ProcessorsVersions_;
import ru.alcereo.usability.predicates.*;
import tests.firsttest.config.TestConfig;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

/**
 * Created by alcereo on 27.04.17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class UsabilityTests {

    @Autowired
    private QueryBuilder qBuilder;

    @BeforeClass
    public static void initDB() {
//        QueryBuilderTest.initData(factory);
    }


    @Test
    public void first(){

        final String PARAMETERS = "PARAMETERS_LINK";

        Attributive<?,ParametersEntity> parametersTable = new Attributive<>("PARAMETERS_LINK");
        Attributive<ParametersEntity,Integer> parameters_id = new Attributive<>(parametersTable, "id");
        Attributive<ParametersEntity,String> parameters_name = new Attributive<>(parametersTable, "name");


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

//        Attribute constaruct

        UPredicate mainPredicate;

        mainPredicate =
                Parameters_.name().in("latency", "count")
                        .and(Parameters_.id().equal(1)
                                        .or(Parameters_.id().greaterThan(3))
                                        .or(Parameters_.id().greaterThan(4))
                        ).and(Parameters_.name().equal("Alcereo")
                );

//      -----

        System.out.println("!!!+");
        mainPredicate.getLinks().forEach(System.out::println);
        System.out.println("!!!-");

//      -----

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
    public void testForFullUsability(){

        ProcessorsVersions_
//                TODO: qBuilder - будет инжектиться
                .select(qBuilder)
                .where(
                        Commands_.id().in(1,2)
                        .or(
                                Commands_.name().in("admin", "some other")
                        )
                )
                .getResultList()
                .forEach(System.out::println);

    }

    @Test
    public void testForUsabilityOfPagination(){

        ProcessorsVersions_
                .select(qBuilder)
                .setPagination(0, 1)
                .getResultList()
                .forEach(System.out::println);

        ProcessorsVersions_
                .select(qBuilder)
                .setPagination(1, 2)
                .getResultList()
                .forEach(System.out::println);

    }

    @Test
    public void testForUsabilityOfOrdering(){
        Function2<CriteriaBuilder, Root<ProcessorsVersionsEntity>, Order> orderFunction =
                (cb, root) -> cb.desc(root.get("name"));

        ProcessorsVersions_
                .select(qBuilder)
                .addOrder(orderFunction)
                .getResultList()
                .forEach(System.out::println);
    }

}
