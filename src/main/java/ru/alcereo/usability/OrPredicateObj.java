package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alcereo on 27.04.17.
 */
public class OrPredicateObj implements Predicate_Obj {

    private List<Predicate_Obj> predicateObjs = new ArrayList<>();

    public OrPredicateObj() {
    }

    public OrPredicateObj(Predicate_Obj... predicateObjs) {
        this.predicateObjs.addAll(Arrays.asList(predicateObjs));
    }

    public List<Predicate_Obj> OrPredicateObj() {
        return predicateObjs;
    }

    public void setPredicateObjs(List<Predicate_Obj> predicateObjs) {
        this.predicateObjs = predicateObjs;
    }

    @Override
    public Predicate buildCriteriaPredicate(final CriteriaBuildData data) {
        CriteriaBuilder cb = data.getCb();

        return cb.or(predicateObjs
                .stream()
                .map(predicate_obj -> predicate_obj.buildCriteriaPredicate(data))
                .toArray(Predicate[]::new)
        );

    }

    @Override
    public Set<String> getLinks() {
        return predicateObjs
                .stream()
                .map(Predicate_Obj::getLinks)
                .reduce(
                        new HashSet<>(),
                        (linkSet1, linkSet2) -> {
                            linkSet1.addAll(linkSet2);
                            return linkSet1;
                        }
                );
    }

    public List<Predicate_Obj> getPredicateObjs() {
        return predicateObjs;
    }

}
