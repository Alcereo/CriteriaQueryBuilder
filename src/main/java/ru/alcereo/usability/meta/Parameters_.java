package ru.alcereo.usability.meta;

import org.hibernate.SessionFactory;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.usability.Attributive;

import javax.persistence.metamodel.*;

/**
 * Created by alcereo on 28.04.17.
 */
public class Parameters_ {

    private static volatile SingularAttribute<ParametersEntity, Integer> id;
    private static volatile SingularAttribute<ParametersEntity, String> name;

    static {
        SessionFactory factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        id = (SingularAttribute<ParametersEntity, Integer>)
                factory.getMetamodel()
                .entity(ParametersEntity.class)
                .getSingularAttribute("id", Integer.class);

        name = (SingularAttribute<ParametersEntity, String>)
                factory.getMetamodel()
                        .entity(ParametersEntity.class)
                        .getSingularAttribute("name", String.class);
    }


    public static Attributive<?,ParametersEntity> table(){
        Attributive<?, ParametersEntity> result = new Attributive<>();
        result.setViewName(ParametersEntity.class.getName());

        return result;
    }

    public static Attributive<ParametersEntity,Integer> id(){
        Attributive<ParametersEntity, Integer> result = new Attributive<>();

        result.setParent(table());
        result.setAttribute(id);

        return result;
    }

    public static Attributive<ParametersEntity, String> name(){
        Attributive<ParametersEntity, String> result = new Attributive<>();

        result.setParent(table());
        result.setAttribute(name);

        return result;
    }




}
