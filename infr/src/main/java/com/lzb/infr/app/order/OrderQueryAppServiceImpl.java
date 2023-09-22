package com.lzb.infr.app.order;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lzb.app.common.PageDto;
import com.lzb.app.order.query.OrderQueryAppService;
import com.lzb.app.order.query.dto.OrderQueryDto;
import com.lzb.app.order.query.view.OrderDetailView;
import com.lzb.app.order.query.view.OrderView;
import com.lzb.infr.app.order.converter.OrderViewConverter;
import com.lzb.infr.domain.order.persistence.po.OrderPo;
import com.lzb.infr.domain.order.persistence.service.OrderPoService;
import io.micrometer.common.util.StringUtils;
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

    @Override
    public PageDto<OrderView> listForPage(OrderQueryDto queryDto) {
        LambdaQueryWrapper<OrderPo> query = Wrappers.lambdaQuery(OrderPo.class);
        query.in(CollectionUtils.isNotEmpty(queryDto.orderIds()), OrderPo::getOrderId, queryDto.orderIds());
        query.likeRight(StringUtils.isNotBlank(queryDto.email()), OrderPo::getEmail, queryDto.email());
        Page<OrderPo> page = orderPoService.page(new Page<>(queryDto.pageIndex(), queryDto.pageSize()), query);
        return PageDto.of(page.getPages(), page.getSize(), page.getTotal(), page.getRecords(), OrderViewConverter::convert);
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
