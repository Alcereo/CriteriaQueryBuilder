package ru.alcereo.futils;

/**
 * Created by alcereo on 22.04.17.
 */
public class Tuple2<TYPE1, TYPE2> {

    private TYPE1 value1;
    private TYPE2 value2;

    public Tuple2(TYPE1 value1, TYPE2 value2) {
        this.value1 = value1;
        this.value2 = value2;
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
}
