package com.lzb.infr.domain.order.persistence.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lzb.infr.domain.order.persistence.po.OrderPo;
import com.lzb.infr.domain.order.persistence.mapper.OrderPoMapper;

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

}