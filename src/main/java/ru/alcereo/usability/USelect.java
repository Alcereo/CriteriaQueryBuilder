package ru.alcereo.usability;

import ru.alcereo.criteria.OrderInfo;
import ru.alcereo.criteria.PathView;
import ru.alcereo.criteria.QueryBuilder;
import ru.alcereo.usability.predicates.Attributive;
import ru.alcereo.usability.predicates.UPredicate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by alcereo on 30.04.17.
 */
public class USelect<TYPE> {
    private final Class<TYPE> startEntity;
    private List<UPredicate> whitePredicates = new ArrayList<>();
    private List<UPredicate> blackPredicates = new ArrayList<>();

    //пагинация
    private int offset = 0;
    private int pageSize = 1000;

    private List<OrderInfo> ordersInfo = new ArrayList<>();

    public USelect(Class<TYPE> startEntity) {
        this.startEntity = startEntity;
    }

    public USelect<TYPE> where(UPredicate predicate){
        whitePredicates.add(predicate);
        return this;
    }

    public USelect<TYPE> whereNot(UPredicate predicate){
        blackPredicates.add(predicate);
        return this;
    }

    public USelect<TYPE> setPagination(int offset, int pageSize){
        this.offset = offset;
        this.pageSize = pageSize;
        return this;
    }

    public int getPaginationFirstPage() {
        return offset;
    }

    public int getPaginationPageSize() {
        return pageSize;
    }

    public USelect<TYPE> addAscOrder(Attributive attributive){
        return this.addOrder(OrderInfo.Direction.ASC, attributive);
    }

    public USelect<TYPE> addDescOrder(Attributive attributive){
        return this.addOrder(OrderInfo.Direction.DESC, attributive);
    }

    private USelect<TYPE> addOrder(OrderInfo.Direction direction, Attributive attributive){
        this.ordersInfo.add(new OrderInfo(direction, attributive.getAttribute()));
        return this;
    }

    public List<OrderInfo> getOrdersInfo() {
        return ordersInfo;
    }

    public List<TYPE> getResultList(QueryBuilder qBuilder){
        QueryBuilder.QueryData<TYPE> queryData = qBuilder.selectFrom(startEntity);

        whitePredicates
                .stream()
                .map(UPredicate::getLinks)
                .reduce(new HashSet<>(),
                        (setOfLinks1, setOfLinks2) -> {
                            setOfLinks1.addAll(setOfLinks2);
                            return setOfLinks1;
                        })
                .forEach(
                        link -> queryData.addWhiteLink(
                                new PathView(link),
                                link
                        )
                );

        whitePredicates
                .forEach(
                        predicate -> queryData.addWhiteFilter(
                                (cb, links, root) -> predicate.buildCriteriaPredicate(
                                        new CriteriaBuildData(
                                                links,
                                                cb
                                        )
                        ))
                );


        blackPredicates
                .stream()
                .map(UPredicate::getLinks)
                .reduce(new HashSet<>(),
                        (setOfLinks1, setOfLinks2) -> {
                            setOfLinks1.addAll(setOfLinks2);
                            return setOfLinks1;
                        })
                .forEach(
                        link -> queryData.addBlackLink(
                                new PathView(link),
                                link
                        )
                );

        blackPredicates
                .forEach(
                        predicate -> queryData.addBlackFilter(
                                (cb, links, root) -> predicate.buildCriteriaPredicate(
                                        new CriteriaBuildData(
                                                links,
                                                cb
                                        )
                                ))
                );

        ordersInfo.forEach(queryData::addOrder);

        return queryData
                .setPagination(this.offset, this.pageSize)
                .getResultList();
    }
}
