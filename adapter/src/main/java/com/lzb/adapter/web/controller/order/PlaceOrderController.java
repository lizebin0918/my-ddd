package com.lzb.adapter.web.controller.order;

import com.lzb.app.order.cmd.PlaceOrderUseCase;
import com.lzb.app.order.cmd.dto.PlaceOrderDto;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 生单<br/>
 * Created on : 2023-12-11 20:22
 * @author lizebin
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class PlaceOrderController {

    private PlaceOrderUseCase placeOrderUseCase;

    @PutMapping
    Long placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return placeOrderUseCase.placeOrder(placeOrderDto);
    }

}
