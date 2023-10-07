package com.lzb.app.order.cmd;

import com.lzb.app.order.cmd.assemble.OrderAssembler;
import com.lzb.app.order.cmd.dto.UpdateAddressDto;
import com.lzb.app.order.cmd.dto.UpdateFullNameDto;
import com.lzb.domain.order.valobj.FullName;
import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.aggregate.OrderAddress;
import com.lzb.domain.order.repository.OrderRepository;
import jakarta.annotation.Resource;

import org.springframework.stereotype.Service;

/**
 * <br/>
 * Created on : 2023-09-16 10:23
 * @author lizebin
 */
@Service
public class UpdateOrderAddressAppService {

    @Resource
    private OrderRepository orderRepository;

    /**
     * 更新订单地址
     * @param updateOrderAddress
     */
    public void updateAddress(UpdateAddressDto updateOrderAddress) {
        OrderAddress orderAddress = OrderAssembler.toOrderAddress(updateOrderAddress);
        Order order = orderRepository.getOrThrow(orderAddress.getId());
        order.updateAddress(orderAddress);
        orderRepository.update(order);
    }

    /**
     * 只更新姓名
     * @param updateFullName
     */
    public void updateFullName(UpdateFullNameDto updateFullName) {
        long orderId = updateFullName.orderId();
        Order order = orderRepository.getOrThrow(orderId);
        order.updateFullName(FullName.of(updateFullName.firstName(), updateFullName.lastName()));
        orderRepository.update(order);
    }

}
