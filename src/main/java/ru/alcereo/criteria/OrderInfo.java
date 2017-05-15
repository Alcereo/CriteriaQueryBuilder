package ru.alcereo.criteria;

/**
 * Created by maxim_000 on 15.05.2017.
 * Параметр сортировки
 */
public class OrderInfo {
    private Direction direction;
    private String field;

    public OrderInfo(Direction direction, String field) {
        this.direction = direction;
        this.field = field;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getField() {
        return field;
    }

    public enum Direction{
        ASC, DESC
    }
}
