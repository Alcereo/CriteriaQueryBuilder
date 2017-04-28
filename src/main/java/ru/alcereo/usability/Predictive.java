package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.Map;

/**
 * Created by alcereo on 27.04.17.
 */
public interface Predictive {

    Predicate buildCriteriaPredicate(final CriteriaBuildData data);

}
