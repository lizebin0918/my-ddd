package com.lzb.app.order.query;

import com.lzb.app.common.PageDto;
import com.lzb.app.order.query.dto.OrderQueryDto;
import com.lzb.app.order.query.view.OrderDetailView;
import com.lzb.app.order.query.view.OrderView;

/**
 * 订单列表<br/>
 * Created on : 2023-09-06 22:39
 * @author mac
 */
public interface OrderQueryAppService {

    /**
     * 订单列表
     * @param queryDto
     * @return
     */
    PageDto<OrderView> listForPage(OrderQueryDto queryDto);

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    OrderDetailView detail(long orderId);

    long count();
}
