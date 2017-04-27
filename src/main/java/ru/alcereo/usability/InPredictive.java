package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by alcereo on 27.04.17.
 */
public class InPredictive<TYPE> implements Predictive {

    private Attributive<TYPE> attributive;
    private List<TYPE> subjects;

    public InPredictive(Attributive<TYPE> attributive, List<TYPE> subjects) {
        this.attributive = attributive;
        this.subjects = subjects;
    }

    public InPredictive() {
    }

    @Override
    public Predicate buildCriteriaPredicate(CriteriaBuilder cb) {

        CriteriaBuilder.In<TYPE> result = cb.in(attributive.getExpression());

        for (TYPE subject : subjects) {
            result.<TYPE>value(subject);
        }

        return result;
    }

    public Attributive<TYPE> getAttributive() {
        return attributive;
    }

    public void setAttributive(Attributive<TYPE> attributive) {
        this.attributive = attributive;
    }

    public List<TYPE> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<TYPE> subjects) {
        this.subjects = subjects;
    }

}
