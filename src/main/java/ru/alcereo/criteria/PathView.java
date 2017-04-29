package ru.alcereo.criteria;

/**
 * Created by alcereo on 23.04.17.
 */
class PathView {

    private Class classView;
    private final String stringView;
    private String pathName;

    public PathView(Class classView) {
        this.classView = classView;
        this.stringView = classView.getName();
    }

    public PathView(String stringView) {
        this.stringView = stringView;
    }

    public PathView(String stringView, String pathName) {
        this.stringView = stringView;
    }



    public static PathView from(Class classView) {
        return new PathView(classView);
    }

    public static PathView from(String className) {
        return new PathView(className);
    }

    public static PathView from(String className, String pathName) {
        return new PathView(className, pathName);
    }



    public boolean itClassView() {
        return classView != null;
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

        if (!stringView.equals(pathView.stringView)) return false;
        return pathName != null ? pathName.equals(pathView.pathName) : pathView.pathName == null;
    }

    @Override
    public int hashCode() {
        int result = stringView.hashCode();
        result = 31 * result + (pathName != null ? pathName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "PathView{" +
                "classView=" + classView +
                ", stringView='" + stringView + '\'' +
                ", pathName='" + pathName + '\'' +
                '}';
    }
}
