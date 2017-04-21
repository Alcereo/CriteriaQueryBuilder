package firsttest;

import org.hibernate.Interceptor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ProcessorsEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.entities.ru.alcereo.utils.Function2;

import javax.persistence.criteria.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by alcereo on 16.04.17.
 */
public class SelectProcessorsVersionsTest {

    private static SessionFactory factory;

    @BeforeClass
    public static void initFactory(){
        factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Test
    public void selectVersionsTest(){

        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();


        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<ProcessorsVersionsEntity> criteria = builder.createQuery(ProcessorsVersionsEntity.class);

        Root<ProcessorsVersionsEntity> root = criteria.from(ProcessorsVersionsEntity.class);

        criteria.select(root);

        Join processorVersionJoin = root.join(
                "processorsUsed", JoinType.LEFT);

        processorVersionJoin.on(builder.in(processorVersionJoin.get("id")).value(2));

        session.createQuery(criteria).list().forEach((x) -> System.out.println(x+" - "+x.getProcessorsUsed()));

//        ----

        tx.commit();
        session.close();

    }

    @Test
    public void selectVersionsFinalTest(){

        Function2<CriteriaBuilder, Join, Predicate> predicate = (CriteriaBuilder cb, Join join) ->
                cb.and(join.get("id").in(1),join.isNotNull());

        Class<ProcessorsVersionsEntity> StartEntity = ProcessorsVersionsEntity.class;

//      Core

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();


        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<ProcessorsVersionsEntity> criteria = cb.createQuery(StartEntity);
        Root<ProcessorsVersionsEntity> root = criteria.from(StartEntity);

        Join processorJoin = root.join("processorsUsed");

        Join commandJoin = processorJoin.join("commands");

        commandJoin.on(predicate.apply(cb,commandJoin));


//        Security join ++

//        Join paramenterJoin = commandJoin.join("parameters", JoinType.LEFT);
//        paramenterJoin.on(predicate.apply(cb,commandJoin));
//
//        Subquery<CommandsEntity> subqueryCommand = criteria.subquery(CommandsEntity.class);
//        Root subCommand = subqueryCommand.from(CommandsEntity.class);
//        Join subParamsJoin = subCommand.join("parameters", JoinType.LEFT);
//        subqueryCommand.groupBy(subCommand.get("id"));
//        subqueryCommand.having(
//                cb.equal(
//                        cb.<Integer>max(cb.<Integer>selectCase()
//                                .when(
//                                        cb.not(predicate.apply(cb,subParamsJoin)),
//                                        cb.literal(1)
//                                ).<Integer>otherwise(cb.literal(0))
//                        )
//                        ,
//                        cb.literal(0)
//                )
//        );
//        subqueryCommand.select(subCommand.get("id"));

//        --

        CriteriaQuery<ProcessorsVersionsEntity> selectFinish =
                criteria.select(root)
//                .where(commandJoin.in(subqueryCommand))
                .distinct(true);

        session.createQuery(selectFinish).list().forEach((x) -> System.out.println(x));

//        ----

        tx.commit();
        session.close();

    }

    @Test
    public void selectVersionsWithFilerTest(){

        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();


        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<ProcessorsVersionsEntity> criteria = builder.createQuery(ProcessorsVersionsEntity.class);

        Root<ProcessorsVersionsEntity> root = criteria.from(ProcessorsVersionsEntity.class);

        criteria.select(root);

        Join processorVersionJoin = (Join) root.join(
                "processorsUsed", JoinType.LEFT);

        processorVersionJoin.on(builder.in(processorVersionJoin.get("id")).value(2));

        List<ProcessorsVersionsEntity> list = session.createQuery(criteria).list();

//        session.createFilter(list, "where this.id=2");

        list.forEach(processorsVersionsEntity -> {
            processorsVersionsEntity.setProcessorsUsed(
                    new HashSet<>(session.createFilter(processorsVersionsEntity.getProcessorsUsed(), "where this.id=2").<ProcessorsEntity>getResultList())
            );
        });

        list.forEach((x) -> System.out.println(x+" - "+x.getProcessorsUsed()));

//        ----

        tx.commit();
        session.close();

    }


    @Test
    public void selectVersionsWithFetchFilterTest(){

        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();


        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<ProcessorsVersionsEntity> criteria = builder.createQuery(ProcessorsVersionsEntity.class);

        Root<ProcessorsVersionsEntity> root = criteria.from(ProcessorsVersionsEntity.class);

        criteria.select(root);

        Join processorVersionJoin = (Join) root.join(
                "processorsUsed", JoinType.LEFT);

        processorVersionJoin.on(builder.in(processorVersionJoin.get("id")).value(2));


        List<ProcessorsVersionsEntity> list = session.createQuery(criteria).list();


        list.forEach((x) -> System.out.println(x+" - "+x.getProcessorsUsed()));

//        ----

        tx.commit();
        session.close();

    }
}
