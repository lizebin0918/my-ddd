package com.lzb.infr.order.persistence.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lzb.infr.order.persistence.po.OrderDetailPo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lizebin
 * @since 2023-08-30
 */
public interface IOrderDetailPoService extends IService<OrderDetailPo> {

    List<OrderDetailPo> listByOrderId(long id);

}
