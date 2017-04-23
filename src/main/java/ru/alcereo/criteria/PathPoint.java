package ru.alcereo.criteria;

/**
 * Created by alcereo on 23.04.17.
 */
class PathPoint {

    private boolean simpleJoin;
    private String propertyName;
    private String linkName;

    public PathPoint(boolean simpleJoin, String propertyName, String linkName) {
        this.simpleJoin = simpleJoin;
        this.propertyName = propertyName;
        this.linkName = linkName;
    }

    public PathPoint() {
    }

    public boolean isSimpleJoin() {
        return simpleJoin;
    }

    public void setSimpleJoin(boolean simpleJoin) {
        this.simpleJoin = simpleJoin;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

}
