package com.lzb.app.order.cmd;

import com.lzb.domain.order.aggregate.Order;
import com.lzb.domain.order.aggregate.OrderAddress;
import com.lzb.domain.order.aggregate.builder.OrderAddressBuilder;
import com.lzb.domain.order.dto.UpdateOrderAddressDto;
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
    public void updateOrderAddress(UpdateOrderAddressDto updateOrderAddress) {
        long orderId = updateOrderAddress.orderId();
        Order order = orderRepository.getOrThrow(orderId);
        OrderAddress newOrderAddress = OrderAddressBuilder.newInstance()
                .email(updateOrderAddress.email())
                .phoneNumber(updateOrderAddress.phoneNumber())
                .firstName(updateOrderAddress.firstName())
                .lastName(updateOrderAddress.lastName())
                .addressLine1(updateOrderAddress.addressLine1())
                .addressLine2(updateOrderAddress.addressLine2())
                .country(updateOrderAddress.country())
                .build();
        order.updateAddress(newOrderAddress);
        orderRepository.update(order);
    }
}
