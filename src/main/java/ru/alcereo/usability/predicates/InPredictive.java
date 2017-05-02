package ru.alcereo.usability.predicates;

import ru.alcereo.usability.CriteriaBuildData;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * Created by alcereo on 27.04.17.
 */
public class InPredictive<TYPE> extends SingleAttributedPredicative<TYPE> {

    private List<TYPE> subjects;

    public InPredictive(Attributive<?, TYPE> attributive, List<TYPE> subjects) {
        setAttributive(attributive);
        this.subjects = subjects;
    }

    public InPredictive() {
    }


    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {

        CriteriaBuilder cb = data.getCb();

        CriteriaBuilder.In<TYPE> result = cb.in(getAttributive().getExpression(data));

        for (TYPE subject : subjects) {
            result.<TYPE>value(subject);
        }

        return result;
    }

    public List<TYPE> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TYPE> subjects) {
        this.subjects = subjects;
    }

    public void setSubjects(TYPE... subjects) {
        this.subjects = new ArrayList<>();
        this.subjects.addAll(Arrays.asList(subjects));
    }

}
