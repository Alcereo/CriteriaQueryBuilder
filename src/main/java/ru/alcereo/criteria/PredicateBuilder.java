package ru.alcereo.criteria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.entities.ProcessorsEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.futils.Function2;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * Created by alcereo on 22.04.17.
 */
public class PredicateBuilder<TYPE> {

    private static SessionFactory factory;

    private final Class<TYPE> startEntity;
    private final Map<PathView, String> pathWhiteLinks = new HashMap<>();
    private final Map<PathView, String> pathBlackLinks = new HashMap<>();

    private List<Function2<CriteriaBuilder, Map<String, From>, Predicate>> whitePredicates = new ArrayList<>();
    private List<Function2<CriteriaBuilder, Map<String, From>, Predicate>> blackPredicates = new ArrayList<>();

    private boolean distinct = true;

    public static <TYPE> PredicateBuilder<TYPE> selectFrom(Class<TYPE> entity) {
        return new PredicateBuilder<>(entity);
    }

    public static void setFactory(SessionFactory factory) {
        PredicateBuilder.factory = factory;
    }

    private PredicateBuilder(Class<TYPE> startEntity) {
        this.startEntity = startEntity;
    }


    public PredicateBuilder<TYPE> addWhiteLink(Class entity, String name) {
        pathWhiteLinks.put(
                new PathView(entity),
                name
        );
        return this;
    }

    public PredicateBuilder<TYPE> addWhiteFilter(
            Function2<CriteriaBuilder,
            Map<String, From>, Predicate> predicate) {
        whitePredicates.add(predicate);
        return this;
    }


    public PredicateBuilder<TYPE> addBlackLink(Class entity, String name) {
        pathBlackLinks.put(
                new PathView(entity),
                name
        );
        return this;
    }

    public PredicateBuilder<TYPE> addBlackFilter(Function2<CriteriaBuilder, Map<String, From>, Predicate> predicate) {
        blackPredicates.add(predicate);
        return this;
    }


    public List<TYPE> getResultList() {

        List<TYPE> resultList = null;

        try(Session session = factory.openSession()) {

//        WHITE filter
//            List<TYPE> resultList =
//                    getQuery(session, startEntity, pathWhiteLinks, whitePredicates)
//                            .getResultList();
//
//            if (blackPredicates.size()!=0) {
//                List<TYPE> blackList =
//                        getQuery(session, startEntity, pathBlackLinks, blackPredicates)
//                                .getResultList();
//
//                resultList.removeAll(blackList);
//            }
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<TYPE> criteria = cb.createQuery(startEntity);

            Root<TYPE> mainRoot = criteria.from(startEntity);

            Predicate whitePredicate = buildCriteria(cb, mainRoot, startEntity, pathWhiteLinks, whitePredicates);

            Subquery<TYPE> subquery = criteria.subquery(startEntity);
            Root<TYPE> subQueryRoot = subquery.from(startEntity);

            Predicate blackPredicate = buildCriteria(cb, subQueryRoot, startEntity, pathBlackLinks, blackPredicates);

            Subquery<TYPE> blackListQuery =
                    subquery
                            .select(subQueryRoot)
                            .distinct(true)
                            .where(blackPredicate);

            CriteriaQuery<TYPE> finalQuery =
                    criteria
                            .select(mainRoot)
                            .where(
                                    cb.and(
                                            whitePredicate,
                                            cb.not(mainRoot.in(blackListQuery))
                                    )
                            ).distinct(this.distinct);

            resultList = session
                    .createQuery(finalQuery)
                    .getResultList();

//        BLACK filter

        }

        return resultList;
    }

//    private static<TYPE> Query<TYPE> getQuery(Session session,
//                                              Class<TYPE> startEntity,
//                                              Map<PathView, String> pathLinks,
//                                              List<Function2<CriteriaBuilder, Map<String, From>, Predicate>> predicates
//    ) {
//
//        CriteriaBuilder cb = session.getCriteriaBuilder();
//
//        CriteriaQuery<TYPE> criteria = cb.createQuery(startEntity);
//        Root<TYPE> root = criteria.from(startEntity);
//
//        Map<String, PredicateView> path = getPathMap(startEntity, pathLinks);
//
//        Map<String, From> joins = new HashMap<>();
//
//        joins.put("root", root);
//
//        From lastJoin = root;
//
//        for (Entry<String, PredicateView> entry : path.entrySet()) {
//            PredicateView pView = entry.getValue();
//            String s = entry.getKey();
//
//            if (pView.isSimpleJoin()) {
//                System.out.println("add simple join to " + pView.getPropertyName());
//
//                lastJoin = lastJoin.join(s, JoinType.LEFT);
//
//                joins.put(
//                        pView.getPropertyName(),
//                        lastJoin
//                );
//            }
//        }
//
//        Predicate finalPredicate = cb.and(
//                predicates
//                        .stream()
//                        .map(predicate -> predicate.apply(cb, joins))
//                        .collect(Collectors.toList())
//                        .toArray(new Predicate[0])
//        );
//
//        CriteriaQuery<TYPE> selectFinish =
//                criteria
//                        .select(root)
//                        .where(finalPredicate)
//                        .distinct(true);
//
//        return session.createQuery(selectFinish);
//    }

    private static<TYPE> Predicate buildCriteria(CriteriaBuilder cb,
                                                 Root<TYPE> root,
                                                 Class<TYPE> startEntity,
                                                 Map<PathView, String> pathLinks,
                                                 List<Function2<CriteriaBuilder, Map<String, From>, Predicate>> predicates
    ) {

        Predicate finalPredicate;
        Map<String, From> joins = new HashMap<>();

        List<PredicateView> path = getPathMap(startEntity, pathLinks);

        joins.put("root", root);

        From lastJoin = root;

        for (PredicateView pView : path) {

            System.out.println("add simple join to " + pView.getPropertyName());

            lastJoin = lastJoin.join(pView.getPropertyName(), JoinType.LEFT);

            joins.put(
                    pView.getLinkName(),
                    lastJoin
            );

        }

        finalPredicate = cb.and(
                predicates
                        .stream()
                        .map(predicate -> predicate.apply(cb, joins))
                        .collect(Collectors.toList())
                        .toArray(new Predicate[0])
        );

        return finalPredicate;

    }


    private static<TYPE> List<PredicateView> getPathMap(Class<TYPE> startEntity, Map<PathView, String> pathLinks) {

        List<PredicateView> result = new ArrayList<>();

        //TODO: Пока говнокод для составления карты
        if (startEntity.equals(ProcessorsVersionsEntity.class)) {
            if (
                    pathLinks
                            .keySet()
                            .contains(
                                    PathView.from(ParametersEntity.class)
                            )
                    ) {

                //TODO: Задать определение пути
                result.add(
                        new PredicateView(
                                true,
                        "processorsUsed",
                                pathLinks.get(
                                        PathView.from(ProcessorsEntity.class)
                                )
                        )
                );
                result.add(
                        new PredicateView(
                                true,
                                "commands",
                                pathLinks.get(
                                        PathView.from(CommandsEntity.class)
                                )
                        )
                );
                result.add(
                        new PredicateView(
                        true,
                        "parameters",
                                pathLinks.get(
                                        PathView.from(ParametersEntity.class)
                                )
                        )
                );

            } else if (pathLinks
                    .keySet()
                    .contains(
                            PathView.from(CommandsEntity.class)
                    )) {
                //TODO: Задать определение пути
                result.add(
                        new PredicateView(
                                true,
                                "processorsUsed",
                                pathLinks.get(
                                        PathView.from(ProcessorsEntity.class)
                                )
                        )
                );
                result.add(
                        new PredicateView(
                                true,
                                "commands",
                                pathLinks.get(
                                        PathView.from(CommandsEntity.class)
                                )
                        )
                );
            }
        }


        return result;
    }

    private static class PredicateView {

        private boolean simpleJoin;
        private String propertyName;
        private String linkName;

        public PredicateView(boolean simpleJoin, String propertyName, String linkName) {
            this.simpleJoin = simpleJoin;
            this.propertyName = propertyName;
            this.linkName = linkName;
        }

        public PredicateView() {
        }

        public boolean isSimpleJoin() {
            return simpleJoin;
        }

        public void setSimpleJoin(boolean simpleJoin) {
            this.simpleJoin = simpleJoin;
        }

        public String getPropertyName() {
            return propertyName;
        }

        public void setPropertyName(String propertyName) {
            this.propertyName = propertyName;
        }

        public String getLinkName() {
            return linkName;
        }

        public void setLinkName(String linkName) {
            this.linkName = linkName;
        }
    }

    private static class PathView{

        private Class classView;
        private String stringView;

        public PathView(Class classView) {
            this.classView = classView;
        }

        public PathView(String stringView) {
            this.stringView = stringView;
        }

        public boolean itClassView(){
            return classView!=null;
        }

        static PathView from(Class classView){
            return new PathView(classView);
        }

        public Class getClassView() {
            return classView;
        }

        public String getStringView() {
            return stringView;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PathView pathView = (PathView) o;

            if (classView != null ? !classView.equals(pathView.classView) : pathView.classView != null) return false;
            return stringView != null ? stringView.equals(pathView.stringView) : pathView.stringView == null;
        }

        @Override
        public int hashCode() {
            int result = classView != null ? classView.hashCode() : 0;
            result = 31 * result + (stringView != null ? stringView.hashCode() : 0);
            return result;
        }
    }

}
