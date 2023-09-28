package com.lzb.app.order.cmd;

import com.lzb.app.order.cmd.assemble.OrderAssembler;
import com.lzb.app.order.cmd.dto.PlaceOrderDto;
import com.lzb.component.operation.annotation.OperateLog;
import com.lzb.component.operation.context.OperationLogContext;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.repository.OrderRepository;
import com.lzb.domain.order.service.StockHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

/**
 * 生单<br/>
 * Created on : 2023-09-05 19:53
 * @author lizebin
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlaceOrderAppService {

    private final OrderRepository orders;

    private final StockHandler stockHandler;

    /**
     * 生单
     * @param req
     * @return
     */
    public long placeOrder(PlaceOrderDto req) {
        log.info("place order: {}", req);

        // 生单
        Order order = OrderAssembler.toOrder(req);
        order.place();

        // 锁库存
        stockHandler.lockStock(order);

        orders.add(order);
        return order.getId();
    }

    @OperateLog(bizId = "#bizId", bizType = "#operateType", tableName = "#businessType")
    public void testLog() {
        OperationLogContext.put("bizId", "1");
        OperationLogContext.put("operateType", "日志测试");
        OperationLogContext.put("businessType", "order");
        log.info("test log");
    }

}
