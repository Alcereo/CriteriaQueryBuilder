package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by alcereo on 27.04.17.
 */
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
