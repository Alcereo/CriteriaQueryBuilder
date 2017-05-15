package ru.alcereo.criteria;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import ru.alcereo.futils.*;

import javax.persistence.criteria.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by alcereo on 22.04.17.
 */
public class QueryBuilder {
    private Map<Class, List<Path>> pathsMap = new HashMap<>();
    private SessionFactory factory;

    public QueryBuilder(SessionFactory factory, Map<Class, List<Path>> pathsMap) {
        this.pathsMap = pathsMap;
        this.factory = factory;
    }

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

        public QueryData<TYPE> addWhiteLink(Class entity, String stringLink) {
            pathWhiteLinks.put(
                    new PathView(entity),
                    stringLink
            );
            return this;
        }

        public QueryData<TYPE> addWhiteLink(PathView pathView, String stringLink) {
            pathWhiteLinks.put(
                    pathView,
                    stringLink
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

        public QueryData<TYPE> addBlackLink(PathView pathView, String stringLink) {
            pathBlackLinks.put(
                    pathView,
                    stringLink
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
                lastJoin = lastJoin.join(
                        pathPoint
                                .getPathPointData()
                                .getPropertyName(),
                        JoinType.LEFT
                );
                joins.put(
                        pathPoint.getLinkName(),
                        lastJoin
                );
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

            List<PathPoint> resultPathPoints = new ArrayList<>();

            if (pathLinks.size()==0)
                return resultPathPoints;

//        Берем пути
            List<Path> paths = getPaths(startEntity);

//          Расчитываем пересечение вьюх и точек пути
//         через спец класс
            PointViewsCrossExecutor executor =
                    new PointViewsCrossExecutor(pathLinks, paths)
                            .invoke();

//        TODO: Сделать проход по дереву
//          Пока проходим только по одному пути
            if (executor.getPathMaxDepths().size()>1)
                throw new RuntimeException("Нашли больше одного пути!");

            Path currentPath = executor
                    .getPathMaxDepths().keySet()
                    .stream().findFirst().get();

//        Берем из этого пути заготовленный массив PathPoint-ов
            PathPoint[] fullPathPoints = currentPath.newPathPoints();

//        Берем из мапы максимальный индекс до которого нужно пройтись по этому пути
            Integer maxIndex = executor.getPathMaxDepths().get(currentPath);

//        Заполняем наши точки до нужного индекса
            resultPathPoints
                    .addAll(
                            Arrays.asList(fullPathPoints)
                                    .subList(0, maxIndex)
                    );


//        Заполняем в наших поинтах инфу о линках
            executor.forEachLinkPoint(
                    (pathViewLink, path, index) ->
                            resultPathPoints
                                    .get(index)
                                    .setLinkName(pathViewLink)
            );

            return resultPathPoints;
        }

        private <TYPE> List<Path> getPaths(Class<TYPE> entity) {
            return pathsMap.get(entity);
        }

    }

    /**
     * Класс для поиска PointView-ов в Path-ах
     */
    private static class PointViewsCrossExecutor {

        private Map<PathView, String> pathLinks;
        private List<Path> paths;

        /**
         * Список пар - путь : максимальный индекс, по которому
         * нужно по нему пройтись
         */
        private Map<Path, Integer> pathMaxDepths;

        /**
         * Список троек - Линк, путь, индекс.
         * Содержит информацию в какой индекс пути установить линк.
         */
        private List<Tuple3<String, Path, Integer>> pointsLinksStringIndexes;

        public PointViewsCrossExecutor(Map<PathView, String> pathLinks, List<Path> paths) {
            this.pathLinks = pathLinks;
            this.paths = paths;
        }

        public Map<Path, Integer> getPathMaxDepths() {
            return pathMaxDepths;
        }

        public List<Tuple3<String,Path, Integer>> getPointsLinksStringIndexes() {
            return pointsLinksStringIndexes;
        }

        public PointViewsCrossExecutor invoke() {
            pathMaxDepths = new HashMap<>();
            pointsLinksStringIndexes = new ArrayList<>();

            for (Map.Entry<PathView, String> entry: pathLinks.entrySet()) {
                boolean found = false;
                PathView pathView = entry.getKey();
                String pathLinkName = entry.getValue();

                for (Path path: paths) {

                    Integer depth = path.depthForPathView(pathView);

                    if (depth!=-1){
                        Integer currentDepth = pathMaxDepths.get(path);
                        if (currentDepth==null)
                            currentDepth = depth+1;

//                        Собираем максимальные
//                          индексы поинтов, которые встречаются
                        pathMaxDepths.put(
                                path,
                                Math.max(
                                        depth + 1,
                                        currentDepth)
                        );

//                        Собираем информацию о индексах линков
                        pointsLinksStringIndexes.add(
                                Tuple3.from(
                                        pathLinkName,
                                        path,
                                        depth
                                )
                        );

                        found = true;
                        break;
                    }
                }

    //            Не наши не одного пути для прохода!
    //            АЛЯРМА!!
                if (!found)
                    throw new RuntimeException(
                            "Path for path view not found! path view:"+ pathView
                    );
            }
            return this;
        }

        public void forEachLinkPoint(Consumer3<String, Path, Integer> cons){
            pointsLinksStringIndexes.forEach(
                    stringPathIntegerTuple3 -> cons.accept(
                            stringPathIntegerTuple3.getValue1(),
                            stringPathIntegerTuple3.getValue2(),
                            stringPathIntegerTuple3.getValue3()
                    )
            );
        }

    }
}
