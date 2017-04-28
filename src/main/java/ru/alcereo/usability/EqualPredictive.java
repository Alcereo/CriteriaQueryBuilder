package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

/**
 * Created by alcereo on 28.04.17.
 */
public class EqualPredictive<TYPE> implements Predictive {

    private Attributive<?, TYPE> attributive;
    private TYPE object;

    public EqualPredictive() {
    }

    public EqualPredictive(Attributive<?,TYPE> attributive, TYPE object) {
        this.attributive = attributive;
        this.object = object;
    }

    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {
        CriteriaBuilder cb = data.getCb();

        return cb.equal(attributive.getExpression(data), object);
    }

    public Attributive<?,TYPE> getAttributive() {
        return attributive;
    }

    public void setAttributive(Attributive<?,TYPE> attributive) {
        this.attributive = attributive;
    }

    public TYPE getObject() {
        return object;
    }

    public void setObject(TYPE object) {
        this.object = object;
    }
}
