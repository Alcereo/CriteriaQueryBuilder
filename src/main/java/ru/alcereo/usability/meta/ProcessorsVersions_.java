package ru.alcereo.usability.meta;

import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.usability.USelect;
import ru.alcereo.usability.annotations.UMetaClass;
import ru.alcereo.usability.annotations.UMetaMethod;
import ru.alcereo.usability.predicates.Attributive;

/**
 * Created by alcereo on 30.04.17.
 */
@UMetaClass("ProcessorsVersions")
public class ProcessorsVersions_ extends UBaseMetaClass {

    public static final Attributive<?, ProcessorsVersionsEntity> table =
            new Attributive<>(ProcessorsVersionsEntity.class.getName());

    public static final Attributive<ProcessorsVersionsEntity,Integer> id =
            new Attributive<>(table,"id");

    public static final Attributive<ProcessorsVersionsEntity, String> name =
            new Attributive<>(table,"name");

    @UMetaMethod
    public static Attributive<?,ProcessorsVersionsEntity> table(){
        return table;
    }

    @UMetaMethod
    public static Attributive<ProcessorsVersionsEntity,Integer> id(){
        return id;
    }

    @UMetaMethod
    public static Attributive<ProcessorsVersionsEntity, String> name(){
        return name;
    }

    /**
     * Этого метода быть не должно.
     * Билдер в идеале должен инжектиться.
     */
    public static USelect<ProcessorsVersionsEntity> select(QueryBuilder queryBuilder){
        return baseSelect(queryBuilder, ProcessorsVersionsEntity.class);
    }

}
