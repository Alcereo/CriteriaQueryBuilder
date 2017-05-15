package ru.alcereo.usability.predicates;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import ru.alcereo.usability.CriteriaBuildData;
import ru.alcereo.usability.deserializers.AttributiveDeserializer;

import javax.persistence.criteria.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@JsonDeserialize(using = AttributiveDeserializer.class)
public class Attributive<PARENT_TYPE, SELF_TYPE> {

    private Attributive<?,PARENT_TYPE> parent;
    private String attribute;

    /**
     * Строковая ссылка, которая будет использована при указании на Join
     * Необходима только для аттрибутивов - таблиц
     * ЗЫ для таблиц с указанием пути будет конкатенирована с именем пути
     */
    private String viewName;

    public Attributive(String viewName) {
        this.viewName = viewName;
    }

    public Attributive(Attributive<?, PARENT_TYPE> parent, String attribute) {
        this.parent = parent;
        this.attribute = attribute;
    }

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
        return new InPredictive<>(this, subjects);
    }

    @SafeVarargs
    public final UPredicate in(SELF_TYPE... subjects){
        return new InPredictive<>(this, Arrays.asList(subjects));
    }

    public UPredicate equal(SELF_TYPE subject){
        return new EqualPredictive<>(this, subject);
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

    public String getAttribute() {
        return attribute;
    }

    public void setAttribute(String attribute) {
        this.attribute = attribute;
    }

    public String getViewName() {
        return viewName;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

}
