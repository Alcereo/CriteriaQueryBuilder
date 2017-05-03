package ru.alcereo.usability.meta;

import org.hibernate.SessionFactory;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.usability.annotations.UMetaClass;
import ru.alcereo.usability.annotations.UMetaMethod;
import ru.alcereo.usability.predicates.Attributive;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Created by alcereo on 28.04.17.
 */
@UMetaClass("Commands")
public class Commands_ {

    public static final Attributive<?, CommandsEntity> table;

    static {
        table = new Attributive<>();
        table.setViewName(CommandsEntity.class.getName());
    }

    public static final Attributive<CommandsEntity,Integer> id;
    public static final Attributive<CommandsEntity, String> name;

    static {

        SessionFactory factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        SingularAttribute<CommandsEntity, Integer> idAttribute = (SingularAttribute<CommandsEntity, Integer>)
                factory.getMetamodel()
                        .entity(CommandsEntity.class)
                        .getSingularAttribute("id", Integer.class);

        id = new Attributive<>();
        id.setParent(table);
        id.setAttribute(idAttribute);


        SingularAttribute<CommandsEntity, String> nameAttribute = (SingularAttribute<CommandsEntity, String>)
                factory.getMetamodel()
                        .entity(CommandsEntity.class)
                        .getSingularAttribute("name", String.class);

        name = new Attributive<>();
        name.setParent(table);
        name.setAttribute(nameAttribute);
    }


    @UMetaMethod
    public static Attributive<?,CommandsEntity> table(){
        return table;
    }

    @UMetaMethod
    public static Attributive<CommandsEntity,Integer> id(){
        return id;
    }

    @UMetaMethod
    public static Attributive<CommandsEntity, String> name(){
        return name;
    }

}
