package ru.alcereo.usability.meta;

import org.hibernate.SessionFactory;
import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.usability.predicates.Attributive;
import ru.alcereo.usability.USelect;

import javax.persistence.metamodel.SingularAttribute;

/**
 * Created by alcereo on 30.04.17.
 */
public class ProcessorsVersions_ {

    private static volatile SingularAttribute<ProcessorsVersionsEntity, Integer> id;
    private static volatile SingularAttribute<ProcessorsVersionsEntity, String> name;

    static {
        SessionFactory factory = new org.hibernate.cfg.Configuration().configure().buildSessionFactory();
        id = (SingularAttribute<ProcessorsVersionsEntity, Integer>)
                factory.getMetamodel()
                        .entity(ProcessorsVersionsEntity.class)
                        .getSingularAttribute("id", Integer.class);

        name = (SingularAttribute<ProcessorsVersionsEntity, String>)
                factory.getMetamodel()
                        .entity(ProcessorsVersionsEntity.class)
                        .getSingularAttribute("name", String.class);
    }


    public static Attributive<?,ProcessorsVersionsEntity> table(){
        Attributive<?, ProcessorsVersionsEntity> result = new Attributive<>();
        result.setViewName(ProcessorsVersionsEntity.class.getName());

        return result;
    }

    public static Attributive<ProcessorsVersionsEntity,Integer> id(){
        Attributive<ProcessorsVersionsEntity, Integer> result = new Attributive<>();

        result.setParent(table());
        result.setAttribute(id);

        return result;
    }

    public static Attributive<ProcessorsVersionsEntity, String> name(){
        Attributive<ProcessorsVersionsEntity, String> result = new Attributive<>();

        result.setParent(table());
        result.setAttribute(name);

        return result;
    }

    /**
     * Этого метода быть не должно.
     * Билдер в идеале должен инжектиться.
     * @param queryBuilder
     * @return
     * @deprecated
     */
    public static USelect<ProcessorsVersionsEntity> select(QueryBuilder queryBuilder){
        USelect<ProcessorsVersionsEntity> result = new USelect<>(ProcessorsVersionsEntity.class);
        result.setqBuilder(queryBuilder);

        return result;
    }

    /**
     * Будет работать когда будет инжектиться билдер
     * @return
     */
    public static USelect<ProcessorsVersionsEntity> select(){
        USelect<ProcessorsVersionsEntity> result = new USelect<>(ProcessorsVersionsEntity.class);

        return result;
    }

}
