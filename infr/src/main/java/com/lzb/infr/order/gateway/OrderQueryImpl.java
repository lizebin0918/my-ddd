package com.lzb.infr.order.gateway;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.app.common.PageDto;
import com.lzb.app.order.query.dto.QueryOrderDto;
import com.lzb.app.order.query.OrderQuery;
import com.lzb.app.order.query.vo.OrderDetailView;
import com.lzb.app.order.query.vo.OrderDetailViewContext;
import com.lzb.app.order.query.vo.OrderView;
import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.query.ProductQuery;
import com.lzb.domain.order.repository.OrderRepository;
import com.lzb.infr.order.converter.OrderViewConverter;
import com.lzb.infr.order.persistence.po.OrderPo;
import com.lzb.infr.order.persistence.service.OrderPoService;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 订单gateway实现<br/>
 * Created on : 2023-12-19 10:00
 * @author lizebin
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderQueryImpl implements OrderQuery {

    private final OrderPoService orderPoService;

    private final OrderViewConverter orderViewConverter;

    private final OrderRepository orderRepository;

    private final ProductQuery productQuery;

    @Override
    public PageDto<OrderView> listForPage(QueryOrderDto queryDto) {
        Page<OrderPo> page = orderPoService.listForPage(queryDto);
        return PageDto.of(page.getPages(),
                page.getSize(),
                page.getTotal(),
                page.getRecords(),
                orderPo -> orderViewConverter.convert(orderPo.getOrderId()));
    }

    @Override
    public OrderDetailView detail(long orderId) {
        Order order = orderRepository.getInCache(orderId).orElseThrow();
        OrderDetailViewContext context = OrderDetailViewContext.builder()
                .sku(() -> productQuery.list(order.getOrderDetails().getSkuIds()))
                .build();
        return OrderDetailView.builder().order(order).orderDetailViewContext(context).build();
    }

    @Override
    public long count() {
        return orderPoService.count();
    }
}
