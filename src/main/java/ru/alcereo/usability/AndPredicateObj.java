package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
    public Predicate buildCriteriaPredicate(final CriteriaBuilder cb) {

        return cb.and((Predicate[]) predicateObjs
                .stream()
                .map(predicate_obj -> predicate_obj.buildCriteriaPredicate(cb))
                .toArray()
        );

    }

}
