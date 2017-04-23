package ru.alcereo.criteria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alcereo.entities.CommandsEntity;
import ru.alcereo.entities.ParametersEntity;
import ru.alcereo.entities.ProcessorsEntity;
import ru.alcereo.entities.ProcessorsVersionsEntity;
import ru.alcereo.futils.Function2;
import ru.alcereo.futils.Function3;

import javax.annotation.Resource;
import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alcereo on 22.04.17.
 */
public class QueryBuilder {

    @Autowired
    private SessionFactory factory;

    public void setFactory(SessionFactory factory) {
        this.factory = factory;
    }

    //     * -------------------------------------------------------- *
    //     *                     ОСНОВНЫЕ ФУНКЦИИ                     *
    //     * -------------------------------------------------------- *

    public <TYPE> QueryData<TYPE> selectFrom(Class<TYPE> entity) {
        return new QueryData<>(entity);
    }

    //     * -------------------------------------------------------- *
    //     *                       СЛУЖЕБНЫЕ                          *
    //     * -------------------------------------------------------- *

    private <TYPE> Predicate buildPredicatePrepareRoot(CriteriaBuilder cb,
                                                             Root<TYPE> root,
                                                             Class<TYPE> startEntity,
                                                             Map<PathView, String> pathLinks,
                                                             List<Function3<CriteriaBuilder,
                                                                     Map<String, From>,
                                                                     Root<TYPE>,
                                                                     Predicate>> predicates
    ) {

        Predicate finalPredicate;
        Map<String, From> joins = new HashMap<>();

        List<PathPoint> path = getPathMap(startEntity, pathLinks);

        From lastJoin = root;

        for (PathPoint pathPoint : path) {
            lastJoin = lastJoin.join(pathPoint.getPropertyName(), JoinType.LEFT);
            joins.put(pathPoint.getLinkName(), lastJoin);
        }

        finalPredicate = cb.and(
                predicates
                        .stream()
                        .map(predicate -> predicate.apply(cb, joins, root))
                        .collect(Collectors.toList())
                        .toArray(new Predicate[0])
        );

        return finalPredicate;

    }

    private <TYPE> List<PathPoint> getPathMap(Class<TYPE> startEntity, Map<PathView, String> pathLinks) {

        List<PathPoint> result = new ArrayList<>();

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
                        new PathPoint(
                                true,
                        "processorsUsed",
                                pathLinks.get(
                                        PathView.from(ProcessorsEntity.class)
                                )
                        )
                );
                result.add(
                        new PathPoint(
                                true,
                                "commands",
                                pathLinks.get(
                                        PathView.from(CommandsEntity.class)
                                )
                        )
                );
                result.add(
                        new PathPoint(
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
                        new PathPoint(
                                true,
                                "processorsUsed",
                                pathLinks.get(
                                        PathView.from(ProcessorsEntity.class)
                                )
                        )
                );
                result.add(
                        new PathPoint(
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


    //     * -------------------------------------------------------- *
    //     *                    СЛУЖЕБНЫЕ КЛАССЫ                      *
    //     * -------------------------------------------------------- *

    public class QueryData<TYPE>{

        private final Class<TYPE> startEntity;
        private final Map<PathView, String> pathWhiteLinks = new HashMap<>();
        private final Map<PathView, String> pathBlackLinks = new HashMap<>();

        private List<Function3<CriteriaBuilder, Map<String, From>,Root<TYPE>, Predicate>> whitePredicates = new ArrayList<>();
        private List<Function3<CriteriaBuilder, Map<String, From>,Root<TYPE>, Predicate>> blackPredicates = new ArrayList<>();

        private boolean distinct = true;

        private PaginationData paginationData = new PaginationData(0, 1000);

        private List<Function2<CriteriaBuilder, Root<TYPE>, Order>> ordersFunctions = new ArrayList<>();

        //     * -------------------------------------------------------- *
        //     *                     ОСНОВНЫЕ ФУНКЦИИ                     *
        //     * -------------------------------------------------------- *

        private QueryData(Class<TYPE> startEntity) {
            this.startEntity = startEntity;
        }

        public QueryData<TYPE> addWhiteLink(Class entity, String name) {
            pathWhiteLinks.put(
                    new PathView(entity),
                    name
            );
            return this;
        }

        public QueryData<TYPE> addWhiteFilter(
                Function3<CriteriaBuilder,
                        Map<String, From>,
                        Root<TYPE>,
                        Predicate> predicate) {

            whitePredicates.add(predicate);
            return this;
        }


        public QueryData<TYPE> addBlackLink(Class entity, String name) {
            pathBlackLinks.put(
                    new PathView(entity),
                    name
            );
            return this;
        }

        public QueryData<TYPE> addBlackFilter(
                Function3<CriteriaBuilder,
                        Map<String, From>,
                        Root<TYPE>,
                        Predicate> predicate) {

            blackPredicates.add(predicate);
            return this;
        }

        public QueryData<TYPE> setPagination(int first, int pageSize){
            this.paginationData.setFirst(first);
            this.paginationData.setPageSize(pageSize);

            return this;
        }

        public QueryData<TYPE> addOrder(Function2<CriteriaBuilder, Root<TYPE>, Order> orderLambda){
            this.ordersFunctions.add(orderLambda);

            return this;
        }

        public List<TYPE> getResultList() {

            List<TYPE> resultList = null;

            try(Session session = factory.openSession()) {

                CriteriaBuilder cb = session.getCriteriaBuilder();
                CriteriaQuery<TYPE> criteria = cb.createQuery(startEntity);

                Root<TYPE> mainRoot = criteria.from(startEntity);

                Predicate whitePredicate = buildPredicatePrepareRoot(cb, mainRoot, startEntity, pathWhiteLinks, whitePredicates);

                CriteriaQuery<TYPE> finalQuery = criteria
                        .select(mainRoot)
                        .orderBy()
                        .distinct(distinct);

                if (blackPredicates.size()!=0) {
                    Subquery<TYPE> subQuery = criteria.subquery(startEntity);
                    Root<TYPE> subQueryRoot = subQuery.from(startEntity);

                    Predicate blackPredicate = buildPredicatePrepareRoot(cb, subQueryRoot, startEntity, pathBlackLinks, blackPredicates);

                    Subquery<TYPE> blackListQuery =
                            subQuery
                                    .select(subQueryRoot)
                                    .distinct(true)
                                    .where(blackPredicate);


                    finalQuery.where(
                            cb.and(
                                    whitePredicate,
                                    cb.not(mainRoot.in(blackListQuery))
                            )
                    );

                } else {

                    finalQuery.where(whitePredicate);
                }

                List<Order> orders = ordersFunctions.stream()
                        .map(orderFunction -> orderFunction.apply(cb, mainRoot))
                        .collect(Collectors.toList());

                finalQuery.orderBy(orders);

                resultList = session
                        .createQuery(finalQuery)
                        .setFirstResult(paginationData.getFirst())
                        .setMaxResults(paginationData.getPageSize())
                        .getResultList();

            }

            return resultList;
        }

    }

}
