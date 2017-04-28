package ru.alcereo.usability;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.Map;


public class Attributive<PARENT_TYPE, SELF_TYPE> {

    private Attributive<?,PARENT_TYPE> parent;
    private SingularAttribute<PARENT_TYPE, SELF_TYPE> attributiveName;

    private String viewName;


    public Path<SELF_TYPE> getExpression(final CriteriaBuildData data){
        Map<String, From> links = data.getLinks();
        CriteriaBuilder cb = data.getCb();

        if (parent != null){
            return parent.getExpression(data).get(attributiveName);
        }else {
            return (Path<SELF_TYPE>) links.get(viewName);
        }
    }

}
