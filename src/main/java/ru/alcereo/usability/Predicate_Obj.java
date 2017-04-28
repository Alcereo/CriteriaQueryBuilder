package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Set;

/**
 * Created by alcereo on 27.04.17.
 */
public interface Predicate_Obj {

    Predicate buildCriteriaPredicate(final CriteriaBuildData data);

    Set<String> getLinks();

}
