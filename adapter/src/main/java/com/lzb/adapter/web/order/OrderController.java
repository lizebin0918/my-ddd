package com.lzb.adapter.web.order;

import com.lzb.app.order.cmd.PlaceOrderAppService;
import com.lzb.app.order.cmd.dto.PlaceOrderDto;
import com.lzb.app.order.query.OrderQueryAppService;
import com.lzb.component.dto.ResponseDto;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
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
public record OrderController(
        PlaceOrderAppService placeOrderAppService,
        OrderQueryAppService orderQueryAppService
) {

    @PutMapping
    public Long placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return placeOrderAppService.placeOrder(placeOrderDto);
    }

    @GetMapping("/count")
    public Long count() {
        return orderQueryAppService.count();
    }

}
