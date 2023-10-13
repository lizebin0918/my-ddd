package com.lzb.adapter.web.controller.test;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;

import com.lzb.adapter.web.annotation.MyResponseBody;
import com.lzb.component.utils.json.JsonUtils;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <br/>
 * Created on : 2023-09-12 19:58
 * @author lizebin
 */
@Slf4j
@Validated
@RestController
@RequestMapping
@RequiredArgsConstructor
public class TestOrderController {

    @MyResponseBody
    @PostMapping("/test")
    public TestOrderResult test(@RequestBody @Validated TestOrder order) {
        log.info("测试订单 {}", JsonUtils.toJSONString(order));
        LocalDateTime time = LocalDateTime.of(2023, 9, 11, 11, 8, 8);
        return TestOrderResult.builder()
                .status(order.getStatus())
                .amount(order.getAmount())
                .localDateTime(time)
                .offsetDateTime(OffsetDateTime.of(time, ZoneOffset.ofHours(8)))
                .build();
    }

    @MyResponseBody
    @PostMapping("/test1")
    public TestOrderResult test1(@RequestBody @Validated TestOrder order, @RequestParam int uid, @RequestParam List<String> ids) {
        log.info("测试订单-1 {}", JsonUtils.toJSONString(order));
        LocalDateTime time = LocalDateTime.of(2023, 9, 11, 11, 8, 8);
        return TestOrderResult.builder()
                .status(order.getStatus())
                .amount(order.getAmount())
                .localDateTime(time)
                .offsetDateTime(OffsetDateTime.of(time, ZoneOffset.ofHours(8)))
                .build();
    }

    @MyResponseBody
    @GetMapping("/test2")
    public void test2(TestQuery testQuery) {
        log.info("testQuery {}", JsonUtils.toJSONString(testQuery));
    }

    @MyResponseBody
    @GetMapping("/name")
    public String getByName(@Size(min = 4, max = 10) String name) {
        return name;
    }

    /**
     * 请求多个参数
     * @param name
     * @param age
     * @return
     */
    @GetMapping("/testMultipleParamter")
    public String testMultipleParamter(String name, String age) {
        return name + ":" + age;
    }

}
