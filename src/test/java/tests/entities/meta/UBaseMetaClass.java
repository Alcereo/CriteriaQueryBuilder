package tests.entities.meta;

import ru.alcereo.usability.USelect;

/**
 * Created by alcereo on 03.05.17.
 */
abstract class UBaseMetaClass {
    protected static  <TYPE> USelect<TYPE> baseSelect(Class<TYPE> entityClass){
        return new USelect<>(entityClass);
    }
}
