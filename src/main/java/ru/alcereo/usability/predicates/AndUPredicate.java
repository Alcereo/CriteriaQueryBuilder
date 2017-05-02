package ru.alcereo.usability.predicates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.alcereo.usability.CriteriaBuildData;
import ru.alcereo.usability.annotations.UPredicateView;
import ru.alcereo.usability.deserializers.AndUPredicateDeserializer;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.*;

/**
 * Created by alcereo on 27.04.17.
 */
@JsonDeserialize(using = AndUPredicateDeserializer.class)
@UPredicateView("and")
public class AndUPredicate extends CompoundUPredicate{

    public AndUPredicate(List<UPredicate> childPredicates) {
        super(childPredicates);
    }

    public AndUPredicate(UPredicate... childPredicates) {
        super(childPredicates);
    }

    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {

        CriteriaBuilder cb = data.getCb();

        return cb.and(getChildPredicates()
                .stream()
                .map(predicate_obj -> predicate_obj.buildCriteriaPredicate(data))
                .toArray(Predicate[]::new)
        );

    }


}
