package com.lzb.app.order.query;

import com.lzb.app.common.PageDto;
import com.lzb.app.order.query.dto.QueryOrderDto;
import com.lzb.app.order.query.gateway.OrderGateway;
import com.lzb.app.order.query.view.OrderDetailView;
import com.lzb.app.order.query.view.OrderView;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * <br/>
 * Created on : 2023-12-31 15:13
 * @author lizebin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderQueryAppService {

    private final OrderGateway orderGateway;

    /**
     * 订单列表
     * @param queryDto
     * @return
     */
    public PageDto<OrderView> listForPage(QueryOrderDto queryDto) {
        return orderGateway.listForPage(queryDto);
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    public OrderDetailView detail(long orderId) {
        return orderGateway.detail(orderId);
    }

    public long count(){
        return orderGateway.count();
    }

}
