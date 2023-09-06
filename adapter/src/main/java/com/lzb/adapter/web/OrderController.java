package com.lzb.adapter.web;

import com.lzb.app.cmd.PlaceOrderService;
import com.lzb.app.cmd.dto.PlaceOrderCmd;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <br/>
 * Created on : 2023-09-06 13:34
 * @author lizebin
 */
@Controller
@RequestMapping("/order")
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class OrderController {

    private final PlaceOrderService placeOrderService;

    public void placeOrder(@RequestBody PlaceOrderCmd placeOrderCmd) {
        placeOrderService.placeOrder(placeOrderCmd);
    }

}
