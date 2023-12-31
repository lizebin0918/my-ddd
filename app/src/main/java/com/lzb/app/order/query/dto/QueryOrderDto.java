package com.lzb.app.order.query.dto;

import java.util.Set;

/**
 * 订单查询<br/>
 * Created on : 2023-09-06 13:46
 * @author lizebin
 */
public record QueryOrderDto(
        /*
          可以对多个订单号做格式校验？
         */
        Set<String> orderIds,

        /*
          email模糊查询
         */
        String email,
        int pageIndex,
        int pageSize) {

}
