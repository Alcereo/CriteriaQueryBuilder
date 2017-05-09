package unitTests.predicates;

import static org.junit.Assert.*;
import org.junit.Test;
import ru.alcereo.usability.predicates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by maxim_000 on 06.05.2017.
 */
public class AttributiveTest {

    @Test
    public void inListArg_CallWithArgs_CorrectResult(){
        Attributive tableAttributive = new Attributive("SomeTable");
        Attributive testIdAttributive = new Attributive(tableAttributive, "id");
        List<Integer> testSubjects = new ArrayList<>();
        testSubjects.add(1);
        testSubjects.add(2);
        testSubjects.add(3);

        UPredicate result = testIdAttributive.in(testSubjects);

        assertTrue(result instanceof InPredictive);
        InPredictive inPredictiveResult = (InPredictive) result;
        assertInPredicate(testIdAttributive, testSubjects, inPredictiveResult);
    }

    @Test
    public void inVarArgs_CallWithArgs_CorrectResult(){
        Attributive tableAttributive = new Attributive("SomeTable");
        Attributive testIdAttributive = new Attributive(tableAttributive, "id");
        Integer[] testSubjects = new Integer[]{1, 2, 3};

        UPredicate result = testIdAttributive.in(testSubjects);

        assertTrue(result instanceof InPredictive);
        InPredictive inPredictiveResult = (InPredictive) result;
        assertInPredicate(testIdAttributive, Arrays.asList(testSubjects), inPredictiveResult);
    }

    @Test
    public void equal_Call_CorrectResult(){
        Attributive tableAttributive = new Attributive("SomeTable");
        Attributive testIdAttributive = new Attributive(tableAttributive, "id");
        int testSubject = 1;

        UPredicate result = testIdAttributive.equal(testSubject);

        assertTrue(result instanceof EqualPredictive);
        EqualPredictive inPredictiveResult = (EqualPredictive) result;
        assertBinaryPredicative(testIdAttributive, testSubject, inPredictiveResult);
    }

    @Test
    public void greaterThan_Call_CorrectResult(){
        Attributive tableAttributive = new Attributive("SomeTable");
        Attributive testIdAttributive = new Attributive(tableAttributive, "id");
        int testSubject = 1;

        UPredicate result = testIdAttributive.greaterThan(testSubject);

        assertTrue(result instanceof GreaterThanPredictive);
        GreaterThanPredictive inPredictiveResult = (GreaterThanPredictive) result;
        assertBinaryPredicative(testIdAttributive, testSubject, inPredictiveResult);
    }


    private static  void assertInPredicate(Attributive testAttributive, List testSubjects, InPredictive result) {
        assertEquals(testAttributive, result.getAttributive());
        assertEquals(testSubjects, result.getSubjects());
    }

    private static  void assertBinaryPredicative(Attributive testAttributive, Object subject, BinaryPredicative result) {
        assertEquals(testAttributive, result.getAttributive());
        assertEquals(subject, result.getSubject());
    }
}
