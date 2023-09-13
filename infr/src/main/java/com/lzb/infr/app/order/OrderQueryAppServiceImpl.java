package com.lzb.infr.app.order;

import java.util.List;

import com.lzb.app.order.query.OrderQueryAppService;
import com.lzb.app.order.query.dto.OrderDetailView;
import com.lzb.app.order.query.dto.OrderQuery;
import com.lzb.app.order.query.dto.OrderView;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-09-06 22:48
 * @author mac
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderQueryAppServiceImpl implements OrderQueryAppService {

    @Override
    public List<OrderView> list(OrderQuery query) {
        return null;
    }

    @Override
    public OrderDetailView detail(long orderId) {
        return null;
    }
}
