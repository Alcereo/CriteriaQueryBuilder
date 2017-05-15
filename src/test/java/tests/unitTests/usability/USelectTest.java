package tests.unitTests.usability;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alcereo.criteria.QueryBuilder;
import tests.entities.ProcessorsVersionsEntity;
import ru.alcereo.futils.Function2;
import ru.alcereo.usability.USelect;
import tests.entities.meta.ProcessorsVersions_;
import tests.unitTests.TestConfig;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import static org.junit.Assert.*;

/**
 * Created by maxim_000 on 15.05.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class USelectTest {

    @Test
    public void setPagination_CorrectParams_SetFields() {
        int offset = 1;
        int pageSize = 1000;

        USelect uSelect = ProcessorsVersions_.select()
                .setPagination(offset, pageSize);

        assertEquals(offset, uSelect.getPaginationFirstPage());
        assertEquals(pageSize, uSelect.getPaginationPageSize());
    }

    @Test
    public void addOrderFunctions_addOneFunction_SetField(){
        Function2<CriteriaBuilder, Root<ProcessorsVersionsEntity>, Order> orderFunction =
                (cb, root) -> cb.asc(root.get("name"));

        USelect uSelect = ProcessorsVersions_.select()
                .addOrder(orderFunction);

        assertEquals(1, uSelect.getOrdersFunctions().size());
        assertEquals(orderFunction, uSelect.getOrdersFunctions().get(0));
    }

    @Test
    public void addOrderFunctions_addSomeFunctions_SetField(){
        Function2<CriteriaBuilder, Root<ProcessorsVersionsEntity>, Order> orderFunction1 =
                (cb, root) -> cb.asc(root.get("id"));
        Function2<CriteriaBuilder, Root<ProcessorsVersionsEntity>, Order> orderFunction2 =
                (cb, root) -> cb.asc(root.get("name"));

        USelect uSelect = ProcessorsVersions_.select()
                .addOrder(orderFunction1)
                .addOrder(orderFunction2);

        assertEquals(2, uSelect.getOrdersFunctions().size());
        assertEquals(orderFunction1, uSelect.getOrdersFunctions().get(0));
        assertEquals(orderFunction2, uSelect.getOrdersFunctions().get(1));
    }
}
