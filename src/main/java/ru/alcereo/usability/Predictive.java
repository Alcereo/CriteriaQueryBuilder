package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * Created by alcereo on 27.04.17.
 */
public interface Predictive {

    Predicate buildCriteriaPredicate(final CriteriaBuilder cb);

}
