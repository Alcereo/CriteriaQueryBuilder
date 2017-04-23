package ru.alcereo.criteria;

/**
 * Created by alcereo on 23.04.17.
 */
class PaginationData {
    private int first;
    private int pageSize;

    public PaginationData(int first, int pageSize) {
        this.first = first;
        this.pageSize = pageSize;
    }

    public PaginationData() {
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
