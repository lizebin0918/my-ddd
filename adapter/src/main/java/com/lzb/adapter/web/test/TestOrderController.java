package com.lzb.adapter.web.test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import com.lzb.adapter.annotation.MyResponseBody;
import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br/>
 * Created on : 2023-09-12 19:58
 * @author lizebin
 */
@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__(@Lazy))
public class TestOrderController {

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
