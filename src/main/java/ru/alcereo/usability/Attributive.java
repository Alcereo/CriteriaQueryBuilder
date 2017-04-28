package ru.alcereo.usability;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Map;


public class Attributive<PARENT_TYPE, SELF_TYPE> {

    private Attributive<?,PARENT_TYPE> parent;
    private SingularAttribute<PARENT_TYPE, SELF_TYPE> attribute;

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
