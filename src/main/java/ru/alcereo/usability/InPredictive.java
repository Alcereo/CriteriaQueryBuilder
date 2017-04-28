package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by alcereo on 27.04.17.
 */
public class InPredictive<TYPE> implements Predictive {

    private Attributive<?, TYPE> attributive;
    private List<TYPE> subjects;

    public InPredictive(Attributive<?, TYPE> attributive, List<TYPE> subjects) {
        this.attributive = attributive;
        this.subjects = subjects;
    }

    public InPredictive() {
    }

    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {

        CriteriaBuilder cb = data.getCb();

        CriteriaBuilder.In<TYPE> result = cb.in(attributive.getExpression(data));

        for (TYPE subject : subjects) {
            result.<TYPE>value(subject);
        }

        return result;
    }

    @Override
    public Set<String> getLinks() {
        Set<String> result = new HashSet<>();
        result.add(attributive.getLink());

        return result;
    }


    public Attributive<?,TYPE> getAttributive() {
        return attributive;
    }

    public void setAttributive(Attributive<?,TYPE> attributive) {
        this.attributive = attributive;
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
