package com.lzb.infr.order.persistence.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.app.order.query.dto.QueryOrderDto;
import com.lzb.infr.order.persistence.mapper.OrderPoMapper;
import com.lzb.infr.order.persistence.po.OrderPo;
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

    public Page<OrderPo> listForPage(QueryOrderDto queryDto) {
        LambdaQueryWrapper<OrderPo> query = Wrappers.lambdaQuery(OrderPo.class);
        query.in(CollectionUtils.isNotEmpty(queryDto.orderIds()), OrderPo::getOrderId, queryDto.orderIds());
        query.likeRight(StringUtils.isNotBlank(queryDto.email()), OrderPo::getEmail, queryDto.email());
        return page(new Page<>(queryDto.pageIndex(), queryDto.pageSize()), query);
    }

}