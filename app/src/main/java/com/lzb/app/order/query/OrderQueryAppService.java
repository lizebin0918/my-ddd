package com.lzb.app.order.query;

import com.lzb.app.common.PageDto;
import com.lzb.app.order.query.dto.QueryOrderDto;
import com.lzb.app.order.query.vo.OrderDetailView;
import com.lzb.app.order.query.vo.OrderView;
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

    private final OrderQuery orderQuery;

    /**
     * 订单列表
     * @param queryDto
     * @return
     */
    public PageDto<OrderView> listForPage(QueryOrderDto queryDto) {
        return orderQuery.listForPage(queryDto);
    }

    /**
     * 订单详情
     * @param orderId
     * @return
     */
    public OrderDetailView detail(long orderId) {
        return orderQuery.detail(orderId);
    }

    public long count(){
        return orderQuery.count();
    }

}
