package ru.alcereo.usability.predicates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.alcereo.usability.CriteriaBuildData;
import ru.alcereo.usability.annotations.UPredicateView;
import ru.alcereo.usability.deserializers.EqualPredictiveDeserializer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * Created by alcereo on 28.04.17.
 */
@JsonDeserialize(using = EqualPredictiveDeserializer.class)
@UPredicateView("equal")
public class EqualPredictive<TYPE> extends BinaryPredicative<TYPE> {

    public EqualPredictive(Attributive<?, TYPE> attributive, TYPE subject) {
        super(attributive, subject);
    }

    public EqualPredictive() {
    }

    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {
        CriteriaBuilder cb = data.getCb();

        return cb.equal(getAttributive().getExpression(data), getSubject());
    }

}
