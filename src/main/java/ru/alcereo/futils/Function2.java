package ru.alcereo.futils;


/**
 * Represents a function that accepts one two arguments and produces a result.
 *
 * @param <PARAMETER1> the type of the first input parameter to the function
 * @param <PARAMETER2> the type of the second input parameter to the function
 * @param <RESULT> the type of the result of the function
 *
 */
@FunctionalInterface
public interface Function2<PARAMETER1,PARAMETER2, RESULT> {

    /**
     * Applies this function to the given argument.
     *
     * @param parameter1 the function argument
     * @return the function result
     */
    RESULT apply(PARAMETER1 parameter1, PARAMETER2 parameter2);


}
