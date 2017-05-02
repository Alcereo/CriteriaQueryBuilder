package ru.alcereo.usability.predicates;

/**
 * Created by alcereo on 28.04.17.
 */
public abstract class BinaryPredicative<TYPE>
        extends SingleAttributedPredicative<TYPE>{

    private TYPE subject;

    public TYPE getSubject() {
        return subject;
    }

    public void setSubject(TYPE subject) {
        this.subject = subject;
    }



    public BinaryPredicative(Attributive<?, TYPE> attributive, TYPE subject) {
        setAttributive(attributive);
        this.subject = subject;
    }

    public BinaryPredicative() {
    }

}
