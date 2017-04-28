package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;

/**
 * Created by alcereo on 27.04.17.
 */
public class OrUPredicate extends CompoundUPredicate {

    public OrUPredicate(List<UPredicate> childPredicates) {
        super(childPredicates);
    }

    public OrUPredicate(UPredicate... childPredicates) {
        super(childPredicates);
    }

    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {
        CriteriaBuilder cb = data.getCb();

        return cb.or(getChildPredicates()
                .stream()
                .map(predicate_obj -> predicate_obj.buildCriteriaPredicate(data))
                .toArray(Predicate[]::new)
        );

    }

}
