package ru.alcereo.criteria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.entities.ProcessorsEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.futils.Function2;
import ru.alcereo.futils.Tuple2;
import ru.alcereo.futils.Tuple3;

import javax.persistence.criteria.*;
import javax.persistence.metamodel.Attribute;
import javax.persistence.metamodel.SingularAttribute;
import java.util.*;
import java.util.Map.Entry;
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

    private PredicateBuilder(Class<TYPE> startEntity) {
        this.startEntity = startEntity;
    }

    public PredicateBuilder<TYPE> addLink(Class entity, String name) {
        joinsContainer.put(entity, name);
        return this;
    }

    public PredicateBuilder<TYPE> addFilter(Function2<CriteriaBuilder, Map<String, From>, Predicate> predicate) {
        predicates.add(predicate);
        return this;
    }

    public static <TYPE> PredicateBuilder<TYPE> selectFrom(Class<TYPE> entity) {
        return new PredicateBuilder<>(entity);
    }

    public List<TYPE> getResultList() {

        Session session = factory.openSession();
        Transaction tx = session.beginTransaction();

        CriteriaBuilder cb = session.getCriteriaBuilder();

        CriteriaQuery<TYPE> criteria = cb.createQuery(startEntity);
        Root<TYPE> root = criteria.from(startEntity);

        Map<String, PredicateView> path = getPathMap();

        joins.put("root", root);

        Map<Subquery, Tuple3<From,String,Join>> subqueries = new HashMap<>();

        From lastJoin = root;

        for (Entry<String, PredicateView> entry : path.entrySet()) {
            PredicateView pView = entry.getValue();
            String s = entry.getKey();

            if (pView.isSimpleJoin()) {
                System.out.println("add simple join to " + pView.getName());

                lastJoin = lastJoin.join(s);

                joins.put(
                        pView.getName(),
                        lastJoin
                );
            } else {
                System.out.println("add simpleJoin join");

                Subquery subquery = criteria.subquery(lastJoin.getJavaType());
                Root subRoot = subquery.from(lastJoin.getJavaType());
                Join subJoin = subRoot.join(s, JoinType.LEFT);

                SingularAttribute id =
                        (SingularAttribute) subRoot.getModel()
                                .getSingularAttributes()
                                .stream()
                                .filter(o -> ((SingularAttribute) o).isId())
                                .findFirst().get();

                subquery.select(subRoot.get(id));
                subquery.groupBy(subRoot.get(id));

                subqueries.put(
                        subquery,
                        new Tuple3<>(
                                lastJoin,
                                pView.getName(),
                                subJoin
                        )
                );

                lastJoin = lastJoin.join(s, JoinType.LEFT);

                joins.put(
                        pView.getName(),
                        lastJoin
                );

            }
        }


//        predicate to subqueries

        Predicate finalPredicate;

        ArrayList<Predicate> wherePredicates = new ArrayList<>();

        for (Entry<Subquery, Tuple3<From, String, Join>> subqueryEntry: subqueries.entrySet()) {
            Subquery subquery = subqueryEntry.getKey();
            From from = subqueryEntry.getValue().getValue1();
            String name = subqueryEntry.getValue().getValue2();
            Join subJoin = subqueryEntry.getValue().getValue3();

            From fromBefore = joins.get(name);
            joins.put(name, subJoin);

            finalPredicate = cb.and(
                    predicates
                            .stream()
                            .map(predicate -> predicate.apply(cb, joins))
                            .collect(Collectors.toList())
                            .toArray(new Predicate[0])
            );

            subquery.having(
                    cb.equal(
                            cb.<Integer>max(cb.<Integer>selectCase()
                                    .when(
                                            cb.not(finalPredicate),
                                            cb.literal(1)
                                    ).<Integer>otherwise(cb.literal(0))
                            )
                            ,
                            cb.literal(0)
                    )
            );

            wherePredicates.add(from.in(subquery));

            joins.put(name, fromBefore);

        }

//        predicate to simple joins

        finalPredicate = cb.and(
                predicates
                        .stream()
                        .map(predicate -> predicate.apply(cb, joins))
                        .collect(Collectors.toList())
                        .toArray(new Predicate[0])
        );

        if (!lastJoin.equals(root))
            ((Join) lastJoin).on(finalPredicate);
        else
            criteria.where(finalPredicate);

        CriteriaQuery<TYPE> selectFinish =
                criteria.select(root)
                        .where(cb.and(wherePredicates.toArray(new Predicate[0])))
                        .distinct(true);

        List<TYPE> resultList = session.createQuery(selectFinish).getResultList();

        return resultList;
    }


    private Map<String, PredicateView> getPathMap() {

        Map<String, PredicateView> result = new LinkedHashMap<>();

        //TODO: Задать определение пути
        result.put("processorsUsed",
                new PredicateView(
                        true,
                        joinsContainer.get(ProcessorsEntity.class)
                ));
        result.put("commands",
                new PredicateView(
                        false,
                        joinsContainer.get(CommandsEntity.class)
        ));
        result.put("parameters", new PredicateView(
                true,
                joinsContainer.get(ParametersEntity.class)
        ));

        return result;
    }

    private class PredicateView {

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
