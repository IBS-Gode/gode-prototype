package org.ibs.cdx.gode.util;


import org.ibs.cdx.gode.entity.BasicEntity;
import org.ibs.cdx.gode.pagination.PageContext;
import org.ibs.cdx.gode.pagination.PagedData;
import org.ibs.cdx.gode.pagination.ResponsePageContext;
import org.ibs.cdx.gode.pagination.Sortable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PageUtils {

    public static <T> PagedData<T> fromPage(Page<T> page) {
        PagedData<T> pagedData = new PagedData<T>();
        pagedData.setData(page.getContent());
        ResponsePageContext ctx = new ResponsePageContext(page.getPageable().getPageSize());
        ctx.setNext(page.hasNext());
        ctx.setPrevious(page.hasPrevious());
        ctx.setPageNumber(page.getPageable().getPageNumber() + 1);
        ctx.setTotalCount(page.getTotalElements());
        ctx.setTotalPages(page.getTotalPages());
        Set<Sortable> sortOrders = new HashSet<>();
        page.getSort().forEach(order->sortOrders.add(fromSort(order)));
        ctx.setSortOrder(sortOrders);
        pagedData.setContext(ctx);
        return pagedData;
    }

    private static Sortable fromSort(Sort.Order order){
        switch (order.getDirection()){
            case ASC: return Sortable.by(Sortable.Type.ASC, order.getProperty());
            case DESC: return Sortable.by(Sortable.Type.DESC, order.getProperty());
            default: return Sortable.by(order.getProperty());
        }
    }

    public static <T extends BasicEntity<?>> PagedData<T> getData(Function<PageRequest, Page<T>> function, PageContext ctx) {
        return fromPage(function.apply(toBaseRequest(ctx)));
    }
    
    public static <T extends BasicEntity<?>,O> PagedData<O> getData(Function<PageRequest, Page<T>> function, PageContext ctx,Function<T,O> map) {
        return fromPage(function.apply(toBaseRequest(ctx)).map(map));
    }

    public static PageRequest toBaseRequest(PageContext context) {
        int pageNo = context.getPageNumber() - 1;
        return context.getSortOrder() == null ? PageRequest.of(pageNo < 0 ? 0 : pageNo, context.getPageSize()) : PageRequest.of(pageNo < 0 ? 0 : pageNo, context.getPageSize(), toBaseSort(context.getSortOrder()));
    }


    public static Sort.Direction toBaseSortDirection(Sortable.Type sortType) {
        switch (sortType){
            case ASC: default: return Sort.Direction.ASC;
            case DESC: return Sort.Direction.DESC;
        }

    }

    public static Sort toBaseSort(Set<Sortable> sortOrders) {
        Sort sort = null;
        for (Sortable sortOrder: sortOrders) {
            if(sort == null) {
                sort = Sort.by(toBaseSortDirection(sortOrder.getSortType()), sortOrder.getField());
            }else{
                sort.and( Sort.by(toBaseSortDirection(sortOrder.getSortType()), sortOrder.getField()));
            }
        }
        return sort;
    }


    public static boolean isEmpty(PagedData<?> page) {
        return page == null || CollectionUtils.isEmpty(page.getData());
    }

    public static <T> PagedData<T> emptyPage(){
        return new PagedData<T>();
    }
}
