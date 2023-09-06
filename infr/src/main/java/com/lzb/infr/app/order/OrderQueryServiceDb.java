package com.lzb.infr.app.order;

import java.util.List;

import com.lzb.app.order.query.OrderQueryService;
import com.lzb.app.order.query.dto.OrderQuery;
import com.lzb.app.order.query.dto.OrderView;

/**
 * <br/>
 * Created on : 2023-09-06 22:48
 * @author mac
 */
public class OrderQueryServiceDb implements OrderQueryService {

    @Override
    public List<OrderView> list(OrderQuery query) {
        return null;
    }
}
