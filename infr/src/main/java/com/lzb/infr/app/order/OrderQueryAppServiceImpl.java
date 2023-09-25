package com.lzb.infr.app.order;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.app.common.PageDto;
import com.lzb.app.order.query.OrderQueryAppService;
import com.lzb.app.order.query.dto.OrderQueryDto;
import com.lzb.app.order.query.view.OrderDetailView;
import com.lzb.app.order.query.view.OrderView;
import com.lzb.infr.app.order.converter.OrderViewConverter;
import com.lzb.infr.domain.order.persistence.po.OrderPo;
import com.lzb.infr.domain.order.persistence.service.OrderPoService;
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
        return null;
    }

    @Override
    public long count() {
        return orderPoService.count();
    }
}
