package ru.alcereo.usability;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.From;
import java.util.Map;

/**
 * Created by alcereo on 28.04.17.
 */
public class CriteriaBuildData {

    private Map<String, From> links;
    private CriteriaBuilder cb;

    public CriteriaBuildData(Map<String, From> links, CriteriaBuilder cb) {
        this.links = links;
        this.cb = cb;
    }

    public CriteriaBuildData() {
    }

    public Map<String, From> getLinks() {
        return links;
    }

    public void setLinks(Map<String, From> links) {
        this.links = links;
    }

    public CriteriaBuilder getCb() {
        return cb;
    }

    public void setCb(CriteriaBuilder cb) {
        this.cb = cb;
    }
}
