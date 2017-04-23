package ru.alcereo.criteria;

/**
 * Created by alcereo on 23.04.17.
 */
class PathView {

    private Class classView;
    private String stringView;

    public PathView(Class classView) {
        this.classView = classView;
    }

    public PathView(String stringView) {
        this.stringView = stringView;
    }

    public boolean itClassView() {
        return classView != null;
    }

    static PathView from(Class classView) {
        return new PathView(classView);
    }

    public Class getClassView() {
        return classView;
    }

    public String getStringView() {
        return stringView;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PathView pathView = (PathView) o;

        if (classView != null ? !classView.equals(pathView.classView) : pathView.classView != null) return false;
        return stringView != null ? stringView.equals(pathView.stringView) : pathView.stringView == null;
    }

    @Override
    public int hashCode() {
        int result = classView != null ? classView.hashCode() : 0;
        result = 31 * result + (stringView != null ? stringView.hashCode() : 0);
        return result;
    }
}
