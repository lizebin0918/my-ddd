package com.lzb.domain.order.repository;

import java.util.List;

import com.lzb.component.domain.repository.CacheRepository;
import com.lzb.component.domain.repository.CommonRepository;
import com.lzb.domain.order.aggregation.Order;

/**
 * <br/>
 * Created on : 2023-08-30 23:07
 * @author mac
 */
public interface OrderRepository extends CommonRepository<Order>, CacheRepository<Order> {

    List<Order> list(long... orderIds);

}
