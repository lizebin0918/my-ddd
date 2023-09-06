package com.lzb.app.order.query;

import java.util.List;

import com.lzb.app.order.query.dto.OrderDetailView;
import com.lzb.app.order.query.dto.OrderView;
import com.lzb.app.order.query.dto.OrderQuery;

/**
 * 订单列表<br/>
 * Created on : 2023-09-06 22:39
 * @author mac
 */
public interface OrderQueryService {

    /**
     * 订单列表
     * @param query
     * @return
     */
    List<OrderView> list(OrderQuery query);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailView detail(long orderId);

}
