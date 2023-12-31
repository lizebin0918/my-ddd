package com.lzb.infr.order.repository;

import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.LongSupplier;

import com.lzb.component.utils.MyDiff;
import com.lzb.component.utils.json.JsonUtils;
import com.lzb.domain.order.aggregation.Order;
import com.lzb.domain.order.repository.OrderRepository;
import com.lzb.infr.common.BaseRepository;
import com.lzb.infr.config.cache.CacheConstants;
import com.lzb.infr.order.converter.OrderConverter;
import com.lzb.infr.order.converter.OrderPoConverter;
import com.lzb.infr.order.persistence.po.OrderDetailPo;
import com.lzb.infr.order.persistence.po.OrderPo;
import com.lzb.infr.order.persistence.service.OrderDetailPoService;
import com.lzb.infr.order.persistence.service.OrderPoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import one.util.streamex.LongStreamEx;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;

import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Repository;

/**
 * <br/>
 * Created on : 2023-08-30 23:08
 * @author mac
 */
@Slf4j
@Repository(OrderRepositoryImpl.BEAN_NAME)
@RequiredArgsConstructor(onConstructor_ = @Lazy)
@CacheConfig(cacheNames = {CacheConstants.ORDER})
public class OrderRepositoryImpl extends BaseRepository<Order> implements OrderRepository {

    public static final String BEAN_NAME = "OrderRepositoryImpl";

    private final OrderPoService orderPoService;

    private final OrderDetailPoService orderDetailPoService;

    @Override
    public LongSupplier doAdd(Order aggregate) {
        OrderPo orderPo = OrderPoConverter.toOrderPo(aggregate);
        orderPoService.save(orderPo);
        orderDetailPoService.saveBatch(OrderPoConverter.toOrderDetailPos(orderPo.getOrderId(), aggregate.getOrderDetails()));
        return orderPo::getOrderId;
    }

    @Override
    @CacheEvict(key = "#order.id")
    public Runnable doUpdate(Order order) {

        ImmutablePair<List<OrderDetailPo>, List<OrderDetailPo>> leftToAddAndRightToUpdate = diffOrderDetailPos(order);
        OrderPo orderPo = OrderPoConverter.toOrderPo(order);
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
        List<OrderDetailPo> currentOrderDetailPos = OrderPoConverter.toOrderDetailPos(order.getId(), order.getOrderDetails());
        List<OrderDetailPo> snapshotOrderDetailPos = OrderPoConverter.toOrderDetailPos(order.getId(), snapshot.getOrderDetails());
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

    /**
     * 不缓存为null的值
     * @param id
     * @return
     */
    @Override
    @Cacheable(key = "#id", unless = "#result == null")
    public Optional<Order> getInCache(long id) {
        return get(id);
    }

    @Override
    public List<Order> list(long... orderIds) {
        if (ArrayUtils.isEmpty(orderIds)) {
            return Collections.emptyList();
        }
        Set<Long> orderIdSet = LongStreamEx.of(orderIds).boxed().toSet();
        List<OrderPo> orders = orderPoService.listByIds(orderIdSet);
        List<OrderDetailPo> orderDetails = orderDetailPoService.listByOrderIds(orderIdSet);
        return OrderConverter.toOrders(orders, orderDetails);
    }
}
