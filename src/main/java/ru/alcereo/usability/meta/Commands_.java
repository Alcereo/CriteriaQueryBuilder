package ru.alcereo.usability.meta;

import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.usability.USelect;
import ru.alcereo.usability.annotations.UMetaClass;
import ru.alcereo.usability.annotations.UMetaMethod;
import ru.alcereo.usability.predicates.Attributive;

/**
 * Created by alcereo on 28.04.17.
 */
@UMetaClass("Commands")
public class Commands_  extends UBaseMetaClass{

    public static final Attributive<?, CommandsEntity> table =
            new Attributive<>(CommandsEntity.class.getName());

    public static final Attributive<CommandsEntity,Integer> id =
            new Attributive<>(table,"id");

    public static final Attributive<CommandsEntity, String> name =
            new Attributive<>(table,"name");

    @UMetaMethod
    public static Attributive<?,CommandsEntity> table(){
        return table;
    }

    @UMetaMethod
    public static Attributive<CommandsEntity,Integer> id(){
        return id;
    }

    @UMetaMethod
    public static Attributive<CommandsEntity, String> name(){
        return name;
    }

    /**
     * Этого метода быть не должно.
     * Билдер в идеале должен инжектиться.
     */
    public static USelect<ParametersEntity> select(QueryBuilder queryBuilder){
        return baseSelect(queryBuilder, ParametersEntity.class);
    }

}
