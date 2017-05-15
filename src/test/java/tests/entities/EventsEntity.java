package tests.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by alcereo on 15.04.17.
 */
@Entity
@Table(name = "events"
//        schema = "public",
//        catalog = "TestDB"
)
public class EventsEntity {
    private int id;
    private String name;
    private Set<ProcessorsEntity> processorUsed;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventsEntity that = (EventsEntity) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }

    @ManyToMany(mappedBy = "events")
    public Set<ProcessorsEntity> getProcessorUsed() {
        return processorUsed;
    }

    public void setProcessorUsed(Set<ProcessorsEntity> processorUsed) {
        this.processorUsed = processorUsed;
    }
}
