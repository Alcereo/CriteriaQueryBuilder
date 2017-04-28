package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.*;

/**
 * Created by alcereo on 28.04.17.
 */
public  class  GreaterThanPredictive<TYPE extends Comparable<? super TYPE>> extends BinaryPredicative<TYPE> {

    public GreaterThanPredictive(Attributive<?, TYPE> attributive, TYPE subject) {
        super(attributive, subject);
    }

    public GreaterThanPredictive() {
    }

    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {
        CriteriaBuilder cb = data.getCb();

        return cb.greaterThan(getAttributive().getExpression(data), getSubject());
    }

}
