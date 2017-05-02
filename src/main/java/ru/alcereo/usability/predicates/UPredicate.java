package ru.alcereo.usability.predicates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.alcereo.usability.CriteriaBuildData;
import ru.alcereo.usability.deserializers.UPredicateDesiarialize;

import javax.persistence.criteria.Predicate;
import java.util.Set;

/**
 * Created by alcereo on 27.04.17.
 */
@JsonDeserialize(using = UPredicateDesiarialize.class)
public interface UPredicate {

    Predicate buildCriteriaPredicate(final CriteriaBuildData data);

    Set<String> getLinks();

//    Cyclic functions

    default AndUPredicate and(UPredicate predicate){
        return new AndUPredicate(
                this,
                predicate
        );
    }

    default OrUPredicate or(UPredicate predicate){
        return new OrUPredicate(
                this,
                predicate
        );
    }

}
