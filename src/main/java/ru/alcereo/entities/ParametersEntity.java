package ru.alcereo.entities;

import javax.persistence.*;

/**
 * Created by alcereo on 15.04.17.
 */
@Entity
@Table(name = "parameters"
//        schema = "public",
//        catalog = "TestDB"
)
public class ParametersEntity {
    private int id;
    private String name;
    private CommandsEntity command;

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

        ParametersEntity that = (ParametersEntity) o;

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
    @JoinColumn(name = "id_command", referencedColumnName = "id", nullable = false)
    public CommandsEntity getCommand() {
        return command;
    }

    public void setCommand(CommandsEntity command) {
        this.command = command;
    }
}
