package com.lzb.adapter.web;

import com.lzb.app.order.cmd.PlaceOrderService;
import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.component.dto.MyReponse;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br/>
 * Created on : 2023-09-06 13:34
 * @author lizebin
 */
@RestController
@RequestMapping("/order")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderController {

    private final PlaceOrderService placeOrderService;

    @PutMapping
    public MyReponse<Long> placeOrder(@RequestBody PlaceOrderReq placeOrderReq) {
        return MyReponse.success(placeOrderService.placeOrder(placeOrderReq));
    }

}
