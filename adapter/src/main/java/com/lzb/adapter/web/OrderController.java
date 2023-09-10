package com.lzb.adapter.web;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.lzb.app.order.cmd.PlaceOrderService;
import com.lzb.app.order.cmd.dto.PlaceOrderReq;
import com.lzb.component.dto.ResponseDto;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
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
    public ResponseDto<Long> placeOrder(@RequestBody PlaceOrderReq placeOrderReq) {
        return ResponseDto.success(placeOrderService.placeOrder(placeOrderReq));
    }

    @PostMapping("/test")
    public ResponseDto<TestOrderResult> test(@RequestBody TestOrder order) {
        return ResponseDto.success(TestOrderResult.builder()
                .status(order.getStatus())
                .amount(order.getAmount())
                .localDateTime(LocalDateTime.of(2023, 1, 1, 1, 1, 1))
                .offsetDateTime(OffsetDateTime.of(2023, 1, 1, 1, 1, 1, 1, ZoneOffset.UTC))
                .build());
    }

}
