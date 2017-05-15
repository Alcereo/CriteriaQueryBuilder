package tests.firsttest;

import com.sun.org.apache.xalan.internal.xsltc.ProcessorVersion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.junit.BeforeClass;
import org.junit.Test;
import tests.entities.CommandsEntity;
import tests.entities.ProcessorsEntity;

import javax.persistence.criteria.*;


public class TestWithDB {

    private static SessionFactory factory;

    @BeforeClass
    public static void initFactory(){
        factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
    }

    @Test
    public void selectProcessorsTest(){

        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<ProcessorsEntity> criteria = builder.createQuery(ProcessorsEntity.class);

        Root<ProcessorsEntity> root = criteria.from(ProcessorsEntity.class);

        criteria.select(root);

        Join<ProcessorsEntity, ProcessorVersion> processorVersionJoin =
                root.join("processorVersion");

        processorVersionJoin.on(builder.in(processorVersionJoin.get("id")).value(1).value(2));

//        criteria.where(builder.equal(root.get("id"),2));

        session.createQuery(criteria).list().forEach((x) -> System.out.println(x+" - "+x.getProcessorVersion()));
//        ----

        tx.commit();
        session.close();

    }


    @Test
    public void selectCommandsTest(){

        Session session = factory.openSession();

        Transaction tx = session.beginTransaction();

        CriteriaBuilder builder = session.getCriteriaBuilder();

        CriteriaQuery<CommandsEntity> criteria = builder.createQuery(CommandsEntity.class);

        Root<CommandsEntity> root = criteria.from(CommandsEntity.class);

        criteria.select(root).distinct(true);

        Join joinToProcessors = root.join("processorsUsed");

        Join joinToVersions = joinToProcessors.join("processorVersion");

//        joinToProcessors.on(builder.in(joinToProcessors.get("id")).value(2));

        joinToVersions.on(builder.in(joinToVersions.get("id")).value(1));

//        criteria.where(builder.equal(root.get("id"),2));

        session.createQuery(criteria).list().forEach((x) -> System.err.println(x));
//        ----

        tx.commit();
        session.close();

    }

}
