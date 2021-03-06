package tests.unitTests.criteria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.path.SetAttributeJoin;
import org.hibernate.query.criteria.internal.path.SingularAttributePath;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.hibernate.cfg.*;
import org.hibernate.query.criteria.internal.predicate.CompoundPredicate;
import org.hibernate.query.criteria.internal.predicate.InPredicate;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alcereo.criteria.QueryBuilder;
import tests.entities.*;
import tests.unitTests.TestConfig;
import tests.unitTests.criteria.fakes.stubs.QueryStub;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.JoinType;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryBuilderTest {

    @Autowired
    private QueryBuilder qBuilder;

    @Autowired
    private SessionFactory stubbedSessionFactory;

    private static SessionFactory realSessionFactory;

    @BeforeClass
    public static void beforeClass(){
        realSessionFactory = createSessionFactory();
    }


    @Test
    public void resultList_addWhiteFilterWithComparisionPredicate_criteriaWithComparisionPredicate() throws Exception {
        Session session = mock(Session.class);
        when(stubbedSessionFactory.openSession()).thenReturn(session);
        CriteriaBuilderImpl spyCriteriaBuilder = spy(new CriteriaBuilderImpl((SessionFactoryImpl) realSessionFactory));
        when(session.getCriteriaBuilder()).thenReturn(spyCriteriaBuilder);
        CriteriaQuery<CommandsEntity> criteriaQuery = spy(spyCriteriaBuilder.createQuery(CommandsEntity.class));
        when(spyCriteriaBuilder.createQuery(CommandsEntity.class)).thenReturn(criteriaQuery);
        when(session.createQuery(criteriaQuery)).thenReturn(new QueryStub<>());

        qBuilder
                .selectFrom(CommandsEntity.class)
                .addWhiteFilter((cb, links, root) ->
                        cb.greaterThanOrEqualTo(root.get("id"), 2)
                ).getResultList();


        List<Expression<Boolean>> expressions = criteriaQuery.getRestriction().getExpressions();
        assertEquals(1, expressions.size());
        ComparisonPredicate expression = (ComparisonPredicate)expressions.get(0);
        assertEquals(ComparisonPredicate.ComparisonOperator.GREATER_THAN_OR_EQUAL, expression.getComparisonOperator());
    }

    @Test
    public void resultList_addWhiteLink_criteriaWithJoin() throws Exception {
        Session session = mock(Session.class);
        when(stubbedSessionFactory.openSession()).thenReturn(session);
        CriteriaBuilderImpl spyCriteriaBuilder = spy(new CriteriaBuilderImpl((SessionFactoryImpl)realSessionFactory));
        when(session.getCriteriaBuilder()).thenReturn(spyCriteriaBuilder);
        CriteriaQuery<ProcessorsVersionsEntity> criteriaQuery = spy(spyCriteriaBuilder.createQuery(ProcessorsVersionsEntity.class));
        when(spyCriteriaBuilder.createQuery(ProcessorsVersionsEntity.class)).thenReturn(criteriaQuery);
        when(session.createQuery(criteriaQuery)).thenReturn(new QueryStub<>());

        qBuilder
                .selectFrom(ProcessorsVersionsEntity.class)
                .addWhiteLink(ParametersEntity.class, "whiteLink")
                .addWhiteFilter(
                        (cb, links, root) ->
                                cb.or(
                                        links.get("whiteLink")
                                                .get("id")
                                                .in(1)
                                        ,
                                        links.get("whiteLink")
                                                .isNull()
                                )
                )
                .getResultList();

        List<Expression<Boolean>> expressions = criteriaQuery.getRestriction().getExpressions();
        CompoundPredicate orExpression = (CompoundPredicate) expressions.get(0);
        InPredicate inPredicate = (InPredicate) orExpression.getExpressions().get(0);
        SingularAttributePath leftJoin = (SingularAttributePath) inPredicate.getExpression();
        assertEquals(JoinType.LEFT, ((SetAttributeJoin)leftJoin.getPathSource()).getJoinType());
    }

    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(CommandsEntity.class)
                .addAnnotatedClass(ParametersEntity.class)
                .addAnnotatedClass(ProcessorsEntity.class)
                .addAnnotatedClass(EventsEntity.class)
                .addAnnotatedClass(ProcessorsVersionsEntity.class);
        configuration.setProperty("hibernate.dialect",
                "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class",
                "org.h2.Driver");
        configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test");
        configuration.setProperty("hibernate.hbm2ddl.auto", "create");
        SessionFactory sessionFactory = configuration.buildSessionFactory();
        return sessionFactory;
    }
}


