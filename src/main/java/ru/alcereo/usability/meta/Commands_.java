package ru.alcereo.usability.meta;

import org.hibernate.SessionFactory;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.usability.annotations.UMetaClass;
import ru.alcereo.usability.predicates.Attributive;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Created by alcereo on 28.04.17.
 */
@UMetaClass("Commands")
public class Commands_ {

    private static volatile SingularAttribute<CommandsEntity, Integer> id;
    private static volatile SingularAttribute<CommandsEntity, String> name;

    static {
        SessionFactory factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        id = (SingularAttribute<CommandsEntity, Integer>)
                factory.getMetamodel()
                        .entity(CommandsEntity.class)
                        .getSingularAttribute("id", Integer.class);

        name = (SingularAttribute<CommandsEntity, String>)
                factory.getMetamodel()
                        .entity(CommandsEntity.class)
                        .getSingularAttribute("name", String.class);
    }


    static Attributive<?,CommandsEntity> table(){
        Attributive<?, CommandsEntity> result = new Attributive<>();
        result.setViewName(CommandsEntity.class.getName());

        return result;
    }

    public static Attributive<CommandsEntity,Integer> id(){
        Attributive<CommandsEntity, Integer> result = new Attributive<>();

        result.setParent(table());
        result.setAttribute(id);

        return result;
    }

    public static Attributive<CommandsEntity, String> name(){
        Attributive<CommandsEntity, String> result = new Attributive<>();

        result.setParent(table());
        result.setAttribute(name);

        return result;
    }


}
