package com.lzb.infr.domain.order.persistence.service;

import java.util.List;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.app.order.query.dto.OrderQueryDto;
import com.lzb.infr.domain.order.persistence.mapper.OrderPoMapper;
import com.lzb.infr.domain.order.persistence.po.OrderPo;
import io.micrometer.common.util.StringUtils;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2023-08-29
 */
@Service
public class OrderPoService extends ServiceImpl<OrderPoMapper, OrderPo> implements IService<OrderPo> {

    public Page<OrderPo> listForPage(OrderQueryDto queryDto) {
        LambdaQueryWrapper<OrderPo> query = Wrappers.lambdaQuery(OrderPo.class);
        query.in(CollectionUtils.isNotEmpty(queryDto.orderIds()), OrderPo::getOrderId, queryDto.orderIds());
        query.likeRight(StringUtils.isNotBlank(queryDto.email()), OrderPo::getEmail, queryDto.email());
        return page(new Page<>(queryDto.pageIndex(), queryDto.pageSize()), query);
    }

    public void update1() {
        List<String> fields = List.of("order_status", "email");

    }

}