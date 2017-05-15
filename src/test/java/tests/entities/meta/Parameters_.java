package tests.entities.meta;

import ru.alcereo.criteria.QueryBuilder;
import tests.entities.ParametersEntity;
import ru.alcereo.usability.USelect;
import ru.alcereo.usability.annotations.UMetaClass;
import ru.alcereo.usability.annotations.UMetaMethod;
import ru.alcereo.usability.predicates.Attributive;

/**
 * Created by alcereo on 28.04.17.
 */
@UMetaClass("Parameters")
public class Parameters_ extends UBaseMetaClass{

    public static final Attributive<?, ParametersEntity> table =
            new Attributive<>(ParametersEntity.class.getName());

    public static final Attributive<ParametersEntity,Integer> id =
            new Attributive<>(table,"id");

    public static final Attributive<ParametersEntity, String> name =
            new Attributive<>(table,"name");

    @UMetaMethod
    public static Attributive<?,ParametersEntity> table(){
        return table;
    }

    @UMetaMethod
    public static Attributive<ParametersEntity,Integer> id(){
        return id;
    }

    @UMetaMethod
    public static Attributive<ParametersEntity, String> name(){
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
