package com.lzb.domain.order.repository;

import java.util.List;

import com.lzb.domain.common.repository.CacheRepository;
import com.lzb.domain.common.repository.CommonRepository;
import com.lzb.domain.order.aggregate.Order;

/**
 * <br/>
 * Created on : 2023-08-30 23:07
 * @author mac
 */
public interface OrderRepository extends CommonRepository<Order>, CacheRepository<Order> {

    List<Order> list(long... orderIds);

}
