package com.lzb.adapter.web.order;

import com.lzb.app.order.cmd.PlaceOrderAppService;
import com.lzb.app.order.cmd.dto.PlaceOrderDto;
import com.lzb.component.dto.ResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
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

    private final PlaceOrderAppService placeOrderAppService;

    @PutMapping
    public ResponseDto<Long> placeOrder(@RequestBody PlaceOrderDto placeOrderDto) {
        return ResponseDto.success(placeOrderAppService.placeOrder(placeOrderDto));
    }

}
