package ru.alcereo.criteria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.entities.ProcessorsEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.futils.Function2;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Created by alcereo on 22.04.17.
 */
public class PredicateBuilder<TYPE> {

    private static SessionFactory factory;

    private final Class<TYPE> startEntity;
    private final Map<Class, String> joinsContainer = new HashMap<>();

    private final Map<String, From> joins = new HashMap<>();

    private List<Function2<CriteriaBuilder, Map<String, From>, Predicate>> predicates = new ArrayList<>();


    public static void setFactory(SessionFactory factory) {
        PredicateBuilder.factory = factory;
    }

    public PredicateBuilder(Class<TYPE> startEntity) {
        this.startEntity = startEntity;
    }

    public PredicateBuilder<TYPE> addLink(Class entity, String name){
        joinsContainer.put(entity, name);
        return this;
    }

    public PredicateBuilder<TYPE> addFilter(Function2<CriteriaBuilder, Map<String, From>, Predicate> predicate){
        predicates.add(predicate);
        return this;
    }

    public static <TYPE> PredicateBuilder<TYPE> selectFrom(Class<TYPE> entity){
        return new PredicateBuilder<>(entity);
    }

    public List<TYPE> getResultList(){

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<TYPE> criteria = cb.createQuery(startEntity);
        Root<TYPE> root = criteria.from(startEntity);

        Map<String, PredicateView> path = getPathMap();

        joins.put("root", root);

        From[] lastJoin = {root};

        path.forEach((s, pView)->{
            if (pView.isSimpleJoin()){
                System.out.println("add simple join to "+pView.getName());

                lastJoin[0] = lastJoin[0].join(s);

                joins.put(
                        pView.getName(),
                        lastJoin[0]
                );
            }else{
                System.out.println("add simpleJoin join");

            }
        });

        Predicate finalPredicate = cb.and(
                predicates
                        .stream()
                        .map(predicate -> predicate.apply(cb, joins))
                        .collect(Collectors.toList())
                        .toArray(new Predicate[0])
        );

        ((Join)lastJoin[0]).on(finalPredicate);

        CriteriaQuery<TYPE> selectFinish =
                criteria.select(root)
//                .where(commandJoin.in(subqueryCommand))
                .distinct(true);

        List<TYPE> resultList = session.createQuery(selectFinish).getResultList();

        return resultList;
    }


    private Map<String, PredicateView> getPathMap() {

        //TODO: Задать определение пути
        Map<String, PredicateView> path = new LinkedHashMap<>();
        path.put("processorsUsed",
                new PredicateView(
                        true,
                        joinsContainer.get(ProcessorsEntity.class)
                ));
        path.put("commands",
                new PredicateView(
                        true,
                        joinsContainer.get(CommandsEntity.class)
        ));
//        path.put("parameters", new PredicateView(
//                true,
//                joinsContainer.get(ParametersEntity.class)
//        ));

        return path;
    }

    private class PredicateView{

        private boolean simpleJoin;
        private String name;

        public PredicateView(boolean simpleJoin, String name) {
            this.simpleJoin = simpleJoin;
            this.name = name;
        }

        public PredicateView() {
        }

        public boolean isSimpleJoin() {
            return simpleJoin;
        }

        public void setSimpleJoin(boolean simpleJoin) {
            this.simpleJoin = simpleJoin;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
