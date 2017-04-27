package ru.alcereo.usability;

import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Selection;
import java.util.Collection;
import java.util.List;

/**
 * Created by alcereo on 27.04.17.
 */
public class Attributive<TYPE> {

    Object parent;

    public Expression<TYPE> getExpression(){

        return new MockExpression();
    }

}

class MockExpression implements Expression{
    @Override
    public Predicate isNull() {
        return null;
    }

    @Override
    public Predicate isNotNull() {
        return null;
    }

    @Override
    public Predicate in(Object... values) {
        return null;
    }

    @Override
    public Predicate in(Expression[] values) {
        return null;
    }

    @Override
    public Predicate in(Collection values) {
        return null;
    }

    @Override
    public Predicate in(Expression values) {
        return null;
    }

    @Override
    public Expression as(Class type) {
        return null;
    }

    @Override
    public Selection alias(String name) {
        return null;
    }

    @Override
    public boolean isCompoundSelection() {
        return false;
    }

    @Override
    public List<Selection<?>> getCompoundSelectionItems() {
        return null;
    }

    @Override
    public Class getJavaType() {
        return null;
    }

    @Override
    public String getAlias() {
        return null;
    }
}
