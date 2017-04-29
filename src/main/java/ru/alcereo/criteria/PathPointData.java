package ru.alcereo.criteria;

/**
 * Created by alcereo on 29.04.17.
 */
class PathPointData {

    /**
     * Имя поля которое является связью в ентити
     */
    private String propertyName;

    /**
     * Имя по которому можно найти этот поинт-
     * ЗЫ сейчас имя класса
     */
    private String pointName;

    /**
     * Класс ентити на который ссылается поинт
     */
    private Class pointEntity;

    public PathPointData(String propertyName, String pointName) {
        this.propertyName = propertyName;
        this.pointName = pointName;
        try {
            this.pointEntity = Class.forName(pointName);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Не наиден класс для :" + pointName);
        }
    }

    public PathPointData(String propertyName, Class pointEntity) {
        this.propertyName = propertyName;
        this.pointEntity = pointEntity;
        this.pointName = pointEntity.getName();
    }

    public boolean pointEqualToView(PathView pathView) {
        return pointName.equals(pathView.getStringView());
    }

    public PathPointData() {
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPointName() {
        return pointName;
    }

    public void setPointName(String pointName) {
        this.pointName = pointName;
    }

    public Class getPointEntity() {
        return pointEntity;
    }

    public void setPointEntity(Class pointEntity) {
        this.pointEntity = pointEntity;
    }
}
