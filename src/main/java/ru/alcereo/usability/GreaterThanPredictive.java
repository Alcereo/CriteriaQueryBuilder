package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

/**
 * Created by alcereo on 28.04.17.
 */
public  class  GreaterThanPredictive<TYPE extends Comparable<? super TYPE>> implements Predictive {

    private Attributive<?,TYPE> attributive;
    private TYPE object;

    public GreaterThanPredictive(Attributive<?,TYPE> attributive, TYPE object) {
        this.attributive = attributive;
        this.object = object;
    }

    public GreaterThanPredictive() {
    }

    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {
        CriteriaBuilder cb = data.getCb();

        return cb.greaterThan(attributive.getExpression(data), object);
    }
}
