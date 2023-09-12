package com.lzb.adapter.web.order;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.lzb.adapter.annotation.MyResponseBody;
import com.lzb.adapter.web.test.TestOrder;
import com.lzb.adapter.web.test.TestOrderResult;
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

    @MyResponseBody
    @PostMapping("/test")
    public TestOrderResult test(@RequestBody TestOrder order) {
        LocalDateTime time = LocalDateTime.of(2023, 9, 11, 11, 8, 8);
        return TestOrderResult.builder()
                .status(order.getStatus())
                .amount(order.getAmount())
                .localDateTime(time)
                .offsetDateTime(OffsetDateTime.of(time, ZoneOffset.ofHours(8)))
                .build();
    }

}
