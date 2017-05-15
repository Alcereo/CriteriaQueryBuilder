package tests.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by alcereo on 15.04.17.
 */
@Entity
@Table(name = "processors"
//        schema = "public",
//        catalog = "TestDB"
)
public class ProcessorsEntity {
    private int id;
    private String name;
    private ProcessorsVersionsEntity processorVersion;
    private Set<CommandsEntity> commands;
    private Set<EventsEntity> events;

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

        ProcessorsEntity that = (ProcessorsEntity) o;

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

    @ManyToOne
    @JoinColumn(name = "version_id", referencedColumnName = "id", nullable = false)
    public ProcessorsVersionsEntity getProcessorVersion() {
        return processorVersion;
    }

    public void setProcessorVersion(ProcessorsVersionsEntity processorVersion) {
        this.processorVersion = processorVersion;
    }

    @ManyToMany
    @JoinTable(
            name = "processors_commands",
//            catalog = "TestDB",
//            schema = "public",
            joinColumns = @JoinColumn(
                    name = "id_processor",
                    referencedColumnName = "id",
                    nullable = false
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "id_command",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    public Set<CommandsEntity> getCommands() {
        return commands;
    }

    public void setCommands(Set<CommandsEntity> commands) {
        this.commands = commands;
    }

    @ManyToMany
    @JoinTable(
            name = "processors_events",
//            catalog = "TestDB",
//            schema = "public",
            joinColumns = @JoinColumn(
                    name = "id_processor",
                    referencedColumnName = "id",
                    nullable = false),
            inverseJoinColumns = @JoinColumn(
                    name = "id_event",
                    referencedColumnName = "id",
                    nullable = false
            )
    )
    public Set<EventsEntity> getEvents() {
        return events;
    }

    public void setEvents(Set<EventsEntity> events) {
        this.events = events;
    }

    @PostLoad
    void postLoad() {
        System.out.println("postLoad");
    }

    @Override
    public String toString() {
        return "ProcessorsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
