package com.lzb.infr.order.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.infr.order.persistence.po.OrderPo;
import com.lzb.infr.order.persistence.mapper.OrderPoMapper;
import com.lzb.infr.order.persistence.service.IOrderPoService;

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
public class OrderPoService extends ServiceImpl<OrderPoMapper, OrderPo> implements IOrderPoService {

}