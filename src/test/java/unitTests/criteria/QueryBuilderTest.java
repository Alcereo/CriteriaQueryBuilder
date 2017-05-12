package unitTests.criteria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.internal.SessionFactoryImpl;
import org.hibernate.query.criteria.internal.CriteriaBuilderImpl;
import org.hibernate.query.criteria.internal.predicate.ComparisonPredicate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.entities.CommandsEntity;
import unitTests.criteria.config.TestConfig;
import unitTests.criteria.fakes.stubs.QueryStub;

import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class QueryBuilderTest {

    @Autowired
    private QueryBuilder qBuilder;

    @Autowired
    private SessionFactory mockedSessionFactory;

    @Test
    public void resultList_addWhiteFilter_checkCriteria() throws Exception {
        Session session = mock(Session.class);
        when(mockedSessionFactory.openSession()).thenReturn(session);
        CriteriaBuilderImpl cb1 = spy(new CriteriaBuilderImpl((SessionFactoryImpl) new org.hibernate.cfg.Configuration().configure().buildSessionFactory()));
        when(session.getCriteriaBuilder()).thenReturn(cb1);
        CriteriaQuery<CommandsEntity> criteriaQuery = spy(cb1.createQuery(CommandsEntity.class));
        when(cb1.createQuery(CommandsEntity.class)).thenReturn(criteriaQuery);
        when(session.createQuery(criteriaQuery)).thenReturn(new QueryStub());

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
}


