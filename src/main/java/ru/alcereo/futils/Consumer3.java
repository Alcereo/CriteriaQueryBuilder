package ru.alcereo.futils;

/**
 * Created by alcereo on 29.04.17.
 */
@FunctionalInterface
public interface Consumer3<TYPE1, TYPE2, TYPE3> {

    /**
     * Performs this operation on the given arguments.
     *
     */
    void accept(TYPE1 value1, TYPE2 value2, TYPE3 value3);

}
