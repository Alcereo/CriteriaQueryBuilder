package ru.alcereo.usability;

import java.util.*;

/**
 * Created by alcereo on 28.04.17.
 */
public abstract class CompoundUPredicate implements UPredicate {

    protected List<UPredicate> childPredicates = new ArrayList<>();

    public List<UPredicate> getChildPredicates() {
        return childPredicates;
    }

    public void setChildPredicates(List<UPredicate> childPredicates) {
        this.childPredicates = childPredicates;
    }



    public CompoundUPredicate(List<UPredicate> childPredicates) {
        this.childPredicates = childPredicates;
    }

    public CompoundUPredicate(UPredicate... childPredicates) {
        this.childPredicates.addAll(Arrays.asList(childPredicates));
    }


    @Override
    public Set<String> getLinks() {
        return childPredicates
                .stream()
                .map(UPredicate::getLinks)
                .reduce(
                        new HashSet<>(),
                        (linkSet1, linkSet2) -> {
                            linkSet1.addAll(linkSet2);
                            return linkSet1;
                        }
                );
    }

}
