package com.lzb.app.order.cmd;

import com.lzb.app.order.cmd.dto.UpdateOrderAddressCmd;
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

    public void updateOrderAddress(UpdateOrderAddressCmd updateOrderAddressCmd) {

    }
}
