package ru.alcereo.usability.meta;

import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.usability.USelect;

/**
 * Created by alcereo on 03.05.17.
 */
abstract class UBaseMetaClass {

    /**
     * Этого метода быть не должно.
     * Билдер в идеале должен инжектиться.
     * @param queryBuilder
     * @return
     */
    protected static <TYPE> USelect<TYPE> baseSelect(QueryBuilder queryBuilder, Class<TYPE> entityClass){
        USelect<TYPE> result = new USelect<>(entityClass);
        result.setqBuilder(queryBuilder);

        return result;
    }

}
