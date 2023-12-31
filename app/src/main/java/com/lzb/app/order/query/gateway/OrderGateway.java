package com.lzb.app.order.query.gateway;

import com.lzb.app.common.PageDto;
import com.lzb.app.order.query.dto.QueryOrderDto;
import com.lzb.app.order.query.view.OrderDetailView;
import com.lzb.app.order.query.view.OrderView;

/**
 * 订单查询<br/>
 * Created on : 2023-12-31 15:17
 * @author lizebin
 */
public interface OrderGateway {

    /**
     * 订单列表
     * @param queryDto
     * @return
     */
    PageDto<OrderView> listForPage(QueryOrderDto queryDto);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailView detail(long orderId);

    long count();

}
