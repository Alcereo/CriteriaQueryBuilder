package ru.alcereo.usability.predicates;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by alcereo on 28.04.17.
 */
public abstract class SingleAttributedPredicative<TYPE> implements Predictive{

    private Attributive<?, TYPE> attributive;

    public Attributive<?, TYPE> getAttributive() {
        return attributive;
    }

    public void setAttributive(Attributive<?, TYPE> attributive) {
        this.attributive = attributive;
    }

    @Override
    public Set<String> getLinks() {
        Set<String> result = new HashSet<>();
        result.add(attributive.getLink());

        return result;
    }
}
