package com.lzb.infr.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.infr.order.entity.OrderDo;
import com.lzb.infr.order.mapper.OrderMapper;
import com.lzb.infr.order.service.IOrderService;

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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OrderDo> implements IOrderService {

}