package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    public Predicate buildCriteriaPredicate(final CriteriaBuilder cb) {

        return cb.or((Predicate[]) predicateObjs
                .stream()
                .map(predicate_obj -> predicate_obj.buildCriteriaPredicate(cb))
                .toArray()
        );

    }

}
