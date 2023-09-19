package com.lzb.app.order.query;

import java.util.List;

import com.lzb.app.order.query.view.OrderDetailView;
import com.lzb.app.order.query.view.OrderView;
import com.lzb.app.order.query.dto.OrderQueryDto;

/**
 * 订单列表<br/>
 * Created on : 2023-09-06 22:39
 * @author mac
 */
public interface OrderQueryAppService {

    /**
     * 订单列表
     * @param query
     * @return
     */
    List<OrderView> list(OrderQueryDto query);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailView detail(long orderId);

}
