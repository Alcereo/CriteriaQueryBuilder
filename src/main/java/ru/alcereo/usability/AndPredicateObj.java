package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alcereo on 27.04.17.
 */
public class AndPredicateObj implements Predicate_Obj {

    List<Predicate_Obj> predicateObjs = new ArrayList<>();

    public AndPredicateObj() {
    }

    public AndPredicateObj(Predicate_Obj... predicateObjs) {
        this.predicateObjs.addAll(Arrays.asList(predicateObjs));
    }

    public List<Predicate_Obj> getPredicateObjs() {
        return predicateObjs;
    }

    public void setPredicateObjs(List<Predicate_Obj> predicateObjs) {
        this.predicateObjs = predicateObjs;
    }

    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {

        CriteriaBuilder cb = data.getCb();

        return cb.and(predicateObjs
                .stream()
                .map(predicate_obj -> predicate_obj.buildCriteriaPredicate(data))
                .toArray(Predicate[]::new)
        );

    }

    @Override
    public Set<String> getLinks() {
        return predicateObjs
                .stream()
                .map(predicate_obj -> predicate_obj.getLinks())
                .reduce(
                        new HashSet<>(),
                        (linksSet1, linksSet2) -> {
                                linksSet1.addAll(linksSet2);
                                return linksSet1;
                        }
                );
    }

}
