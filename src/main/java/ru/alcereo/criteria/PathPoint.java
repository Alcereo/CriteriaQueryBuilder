package ru.alcereo.criteria;

/**
 * Created by alcereo on 23.04.17.
 */
class PathPoint {

    /**
     * Данные о точке пути
     */
    private PathPointData pathPointData;

    /**
     * Имя ссылки, которую задачи, чтобы использовать этот поинт
     * через набор линков
     * ЗЫ Сейчас - заполняется при
     */
    private String linkName;


    public PathPoint(PathPointData pathPointData, String linkName) {
        this.pathPointData = pathPointData;
        this.linkName = linkName;
    }

    public PathPoint() {
    }

    public PathPointData getPathPointData() {
        return pathPointData;
    }

    public void setPathPointData(PathPointData pathPointData) {
        this.pathPointData = pathPointData;
    }

    public String getLinkName() {
        return linkName;
    }

    public void setLinkName(String linkName) {
        this.linkName = linkName;
    }

}
