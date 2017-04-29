package ru.alcereo.criteria;

import java.util.ArrayList;

/**
 * Created by alcereo on 29.04.17.
 */
public class Path {

    private String pathName;
    private ArrayList<PathPointData> pathPointDataList;
    private PathPoint[] pathPoints;


    public String getPathName() {
        return pathName;
    }

    public void setPathName(String pathName) {
        this.pathName = pathName;
    }

    public ArrayList<PathPointData> getPathPointDataList() {
        return pathPointDataList;
    }

    public void setPathPointDataList(ArrayList<PathPointData> pathPointDataList) {
        this.pathPointDataList = pathPointDataList;
    }

    public Integer depthForPathView(PathView pathView){

        Integer result = 0;

        for (PathPointData pathPointData :
                pathPointDataList) {
            if (pathPointData.pointEqualToView(pathView))
                return result;

            result++;
        }

        return -1;
    }

    public void updatePathPoints() {
        pathPoints = pathPointDataList
                .stream()
                .map(pathPointData -> new PathPoint(pathPointData, ""))
                .toArray(PathPoint[]::new);
    }

    public PathPoint[] newPathPoints() {
        return pathPoints.clone();
    }
}
