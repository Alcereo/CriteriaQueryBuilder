package ru.alcereo.usability;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alcereo on 27.04.17.
 */
public class AndPredicate implements Predicate {

    List<Predicate> predicates = new ArrayList<>();

    public AndPredicate() {
    }

    public AndPredicate(Predicate... predicates) {
        this.predicates.addAll(Arrays.asList(predicates));
    }
}
