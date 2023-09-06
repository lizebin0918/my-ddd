package com.lzb.app.order.query;

import java.util.List;

import com.lzb.app.order.query.dto.OrderView;
import com.lzb.app.order.query.dto.OrderQuery;

/**
 * 订单列表<br/>
 * Created on : 2023-09-06 22:39
 * @author mac
 */
public interface OrderQueryService {

    List<OrderView> list(OrderQuery query);

}
