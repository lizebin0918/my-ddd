package com.lzb.infr.order.app;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.app.common.PageDto;
import com.lzb.app.order.query.OrderQueryAppService;
import com.lzb.app.order.query.dto.OrderQueryDto;
import com.lzb.app.order.query.view.OrderDetailView;
import com.lzb.app.order.query.view.OrderDetailViewContext;
import com.lzb.app.order.query.view.OrderView;
import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.gateway.OrderProductGateway;
import com.lzb.domain.order.repository.OrderRepository;
import com.lzb.infr.order.app.converter.OrderViewConverter;
import com.lzb.infr.order.domian.persistence.po.OrderPo;
import com.lzb.infr.order.domian.persistence.service.OrderPoService;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-09-06 22:48
 * @author mac
 */
@Service
@RequiredArgsConstructor
public class OrderQueryAppServiceImpl implements OrderQueryAppService {

    private final OrderPoService orderPoService;

    private final OrderViewConverter orderViewConverter;

    private final OrderRepository orderRepository;

    private final OrderProductGateway orderProductGateway;

    @Override
    public PageDto<OrderView> listForPage(OrderQueryDto queryDto) {
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
                .sku(() -> orderProductGateway.list(order.getOrderDetails().getSkuIds()))
                .build();
        return OrderDetailView.builder().order(order).orderDetailViewContext(context).build();
    }

    @Override
    public long count() {
        return orderPoService.count();
    }
}
