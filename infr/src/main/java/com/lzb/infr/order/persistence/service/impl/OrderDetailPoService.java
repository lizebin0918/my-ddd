package com.lzb.infr.order.persistence.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.infr.order.persistence.po.OrderDetailPo;
import com.lzb.infr.order.persistence.mapper.OrderDetailPoMapper;
import com.lzb.infr.order.persistence.service.IOrderDetailPoService;

import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lizebin
 * @since 2023-08-30
 */
@Service
public class OrderDetailPoService extends ServiceImpl<OrderDetailPoMapper, OrderDetailPo> implements IOrderDetailPoService {

}
