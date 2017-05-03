package ru.alcereo.entities;

import org.hibernate.annotations.MetaValue;

import javax.persistence.*;
import javax.persistence.criteria.From;
import javax.persistence.criteria.Path;
import java.util.Map;
import java.util.Set;

/**
 * Created by alcereo on 15.04.17.
 */
@Entity
@Table(name = "commands"
//        schema = "public",
//        catalog = "TestDB")
)
public class CommandsEntity {
    private int id;
    private String name;
    private Set<ParametersEntity> parameters;
    private Set<ProcessorsEntity> processorsUsed;

    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Path id(Map<String, From> links){
        return links.get(CommandsEntity.i()).get("id");
    };

    @Basic
    @Column(name = "name", nullable = true, length = 64)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String name(){return "name";};

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommandsEntity that = (CommandsEntity) o;

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

    @OneToMany(mappedBy = "command")
    public Set<ParametersEntity> getParameters() {
        return parameters;
    }

    public void setParameters(Set<ParametersEntity> parameters) {
        this.parameters = parameters;
    }

    @ManyToMany(mappedBy = "commands")
    public Set<ProcessorsEntity> getProcessorsUsed() {
        return processorsUsed;
    }

    public void setProcessorsUsed(Set<ProcessorsEntity> processorsUsed) {
        this.processorsUsed = processorsUsed;
    }

    @Override
    public String toString() {
        return "CommandsEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public static String i(){
        return "CommandEntity";
    }
}
