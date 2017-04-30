package ru.alcereo.usability;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Attributive<PARENT_TYPE, SELF_TYPE> {

    private Attributive<?,PARENT_TYPE> parent;
    private SingularAttribute<PARENT_TYPE, SELF_TYPE> attribute;

    /**
     * Строковая ссылка, которая будет использована при указании на Join
     * Необходима только для аттрибутивов - таблиц
     * ЗЫ для таблиц с указанием пути будет конкатенирована с именем пути
     */
    private String viewName;

    public Path<SELF_TYPE> getExpression(final CriteriaBuildData data){
        Map<String, From> links = data.getLinks();
        CriteriaBuilder cb = data.getCb();

        if (parent != null){
            return parent.getExpression(data).get(attribute);
        }else {
            return (Path<SELF_TYPE>) links.get(viewName);
        }
    }

    public String getLink(){
        if (parent!=null)
            return parent.getLink();
        else
            return viewName;
    }

//    ciclic methods

    public final UPredicate in(List<SELF_TYPE> subjects){
        InPredictive<SELF_TYPE> result = new InPredictive<>();

        result.setAttributive(this);
        result.setSubjects(subjects);

        return result;
    }

    @SafeVarargs
    public final UPredicate in(SELF_TYPE... subjects){
        InPredictive<SELF_TYPE> result = new InPredictive<>();

        result.setAttributive(this);
        result.setSubjects(subjects);

        return result;
    }


    public UPredicate equal(SELF_TYPE subject){
        EqualPredictive<SELF_TYPE> result = new EqualPredictive<>();

        result.setAttributive(this);
        result.setSubject(subject);

        return result;
    }

    public UPredicate greaterThan(SELF_TYPE subject){
        GreaterThanPredictive result = new GreaterThanPredictive<>();

        result.setAttributive(this);
        result.setSubject(subject);

        return result;
    }

//    GS-s

    public Attributive<?, PARENT_TYPE> getParent() {
        return parent;
    }

    public void setParent(Attributive<?, PARENT_TYPE> parent) {
        this.parent = parent;
    }

    public SingularAttribute<PARENT_TYPE, SELF_TYPE> getAttribute() {
        return attribute;
    }

    public void setAttribute(SingularAttribute<PARENT_TYPE, SELF_TYPE> attribute) {
        this.attribute = attribute;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

}
