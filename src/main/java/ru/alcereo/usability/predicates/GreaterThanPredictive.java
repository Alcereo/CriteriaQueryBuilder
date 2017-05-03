package ru.alcereo.usability.predicates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.alcereo.usability.CriteriaBuildData;
import ru.alcereo.usability.annotations.UPredicateView;
import ru.alcereo.usability.deserializers.GreaterThanPredictiveDeserializer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * Created by alcereo on 28.04.17.
 */
@JsonDeserialize(using = GreaterThanPredictiveDeserializer.class)
@UPredicateView("gt")
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
