package ru.alcereo.futils;

/**
 * Created by alcereo on 22.04.17.
 */
public class Tuple3<TYPE1, TYPE2, TYPE3> {

    private TYPE1 value1;
    private TYPE2 value2;
    private TYPE3 value3;

    public Tuple3(TYPE1 value1, TYPE2 value2, TYPE3 value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public static <TYPE1, TYPE2, TYPE3> Tuple3<TYPE1, TYPE2, TYPE3> from(TYPE1 value1, TYPE2 value2, TYPE3 value3){
        return new Tuple3<>(value1, value2, value3);
    }

    public TYPE1 getValue1() {
        return value1;
    }

    public void setValue1(TYPE1 value1) {
        this.value1 = value1;
    }

    public TYPE2 getValue2() {
        return value2;
    }

    public void setValue2(TYPE2 value2) {
        this.value2 = value2;
    }

    public TYPE3 getValue3() {
        return value3;
    }

    public void setValue3(TYPE3 value3) {
        this.value3 = value3;
    }
}
