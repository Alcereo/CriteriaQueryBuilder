package tests.unitTests.usability;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.alcereo.criteria.OrderInfo;
import ru.alcereo.usability.USelect;
import tests.entities.meta.ProcessorsVersions_;
import tests.unitTests.TestConfig;

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
        USelect uSelect = ProcessorsVersions_.select()
                .addAscOrder(ProcessorsVersions_.name());

        assertEquals(1, uSelect.getOrdersInfo().size());
        assertEquals(OrderInfo.Direction.ASC, ((OrderInfo)uSelect.getOrdersInfo().get(0)).getDirection());
        assertEquals(ProcessorsVersions_.name().getAttribute(), ((OrderInfo)uSelect.getOrdersInfo().get(0)).getField());
    }

    @Test
    public void addOrderFunctions_addSomeFunctions_SetField(){
        USelect uSelect = ProcessorsVersions_.select()
                .addAscOrder(ProcessorsVersions_.name())
                .addDescOrder(ProcessorsVersions_.id());

        assertEquals(2, uSelect.getOrdersInfo().size());
        assertEquals(OrderInfo.Direction.ASC, ((OrderInfo)uSelect.getOrdersInfo().get(0)).getDirection());
        assertEquals(ProcessorsVersions_.name().getAttribute(), ((OrderInfo)uSelect.getOrdersInfo().get(0)).getField());
        assertEquals(OrderInfo.Direction.DESC, ((OrderInfo)uSelect.getOrdersInfo().get(1)).getDirection());
        assertEquals(ProcessorsVersions_.id().getAttribute(), ((OrderInfo)uSelect.getOrdersInfo().get(1)).getField());
    }
}
