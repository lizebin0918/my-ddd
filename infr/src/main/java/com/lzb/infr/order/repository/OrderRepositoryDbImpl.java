package com.lzb.infr.order.repository;

import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
import java.util.function.LongSupplier;

import com.lzb.component.utils.MyDiff;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.repository.OrderRepository;
import com.lzb.infr.common.BaseRepository;
import com.lzb.infr.config.cache.CacheConstants;
import com.lzb.infr.order.converter.OrderConverter;
import com.lzb.infr.order.persistence.po.OrderDetailPo;
import com.lzb.infr.order.persistence.po.OrderPo;
import com.lzb.infr.order.persistence.service.OrderDetailPoService;
import com.lzb.infr.order.persistence.service.OrderPoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-08-30 23:08
 * @author mac
 */
@Slf4j
@Repository(OrderRepositoryDbImpl.BEAN_NAME)
@RequiredArgsConstructor
@CacheConfig(cacheNames = {CacheConstants.ORDER})
public class OrderRepositoryDbImpl extends BaseRepository<Order> implements OrderRepository {

    public static final String BEAN_NAME = "orderRepositoryDbImpl";

    private final OrderPoService orderPoService;

    private final OrderDetailPoService orderDetailPoService;

    @Override
    public LongSupplier doAdd(Order aggregate) {
        OrderPo orderPo = OrderConverter.toOrderPo(aggregate);
        orderPoService.save(orderPo);
        orderDetailPoService.saveBatch(OrderConverter.toOrderDetailPos(aggregate.getOrderDetails()));
        return orderPo::getOrderId;
    }

    @Override
    @CacheEvict(key = "#order.id")
    public Runnable doUpdate(Order order) {

        ImmutablePair<List<OrderDetailPo>, List<OrderDetailPo>> leftToAddAndRightToUpdate = diffOrderDetailPos(order);
        OrderPo orderPo = OrderConverter.toOrderPo(order);
        return () -> {
            updateByVersion(orderPo);
            saveAndUpdate(leftToAddAndRightToUpdate.getLeft(), leftToAddAndRightToUpdate.getRight());
        };
    }

    /**
     * diff出需要新增和更新的订单明细
     * @param order
     * @return
     */
    private static ImmutablePair<List<OrderDetailPo>, List<OrderDetailPo>> diffOrderDetailPos(Order order) {
        Order snapshot = order.snapshot();
        List<OrderDetailPo> currentOrderDetailPos = OrderConverter.toOrderDetailPos(order.getOrderDetails());
        List<OrderDetailPo> snapshotOrderDetailPos = OrderConverter.toOrderDetailPos(snapshot.getOrderDetails());
        return MyDiff.diff(OrderDetailPo.class, snapshotOrderDetailPos, currentOrderDetailPos);
    }

    private void saveAndUpdate(List<OrderDetailPo> addOrderDetailPos, List<OrderDetailPo> updateOrderDetailPos) {
        orderDetailPoService.saveBatch(addOrderDetailPos);
        orderDetailPoService.updateBatchById(updateOrderDetailPos);
    }

    private void updateByVersion(OrderPo orderPo) {
        boolean success = orderPoService.updateById(orderPo);
        // 只有订单才需要判断版本号
        if (!success) {
            log.info("根据版本号更新失败 {}", JsonUtils.toJSONString(orderPo));
            throw new ConcurrentModificationException("订单信息发生变化, 修改失败");
        }
    }

    @Override
    public Optional<Order> get(long id) {
        Optional<OrderPo> orderOpt = orderPoService.getOptById(id);
        if (orderOpt.isEmpty()) {
            return Optional.empty();
        }
        OrderPo orderPo = orderOpt.get();
        List<OrderDetailPo> orderDetailPos = orderDetailPoService.listByOrderId(id);
        return Optional.of(OrderConverter.toOrder(orderPo, orderDetailPos));
    }

    @Override
    @Cacheable(key = "#id")
    public Order getInCache(long id) {
        return getOrThrow(id);
    }
}
