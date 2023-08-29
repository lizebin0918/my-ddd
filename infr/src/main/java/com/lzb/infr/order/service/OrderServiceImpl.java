package com.lzb.infr.order.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.infr.order.entity.Order;
import com.lzb.infr.order.mapper.OrderMapper;

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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}