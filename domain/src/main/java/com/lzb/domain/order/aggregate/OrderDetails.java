package com.lzb.domain.order.aggregate;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import cn.hutool.core.lang.Assert;
import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单聚合根关联对象-订单明细<br/>
 * 读方法：public
 * 写方法：default
 * Created on : 2022-03-07 19:57
 *
 * @author lizebin
 */
@Slf4j
@Builder
public class OrderDetails implements Iterable<OrderDetail>, Serializable {

    private final List<OrderDetail> list;

    /**
     * @JsonCreator 用于反序列化
     * @param list
     */
    @JsonCreator
    public OrderDetails(List<OrderDetail> list) {
        Assert.notEmpty(list, "订单明细扩展不能为空");
        this.list = list;
    }

    @Override
    public Iterator<OrderDetail> iterator() {
        return list.iterator();
    }

    public Stream<OrderDetail> toStream() {
        return list.stream();
    }
}