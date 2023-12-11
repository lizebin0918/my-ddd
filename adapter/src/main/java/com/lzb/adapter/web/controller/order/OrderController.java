package com.lzb.adapter.web.controller.order;

import com.lzb.app.order.query.OrderQueryAppService;

import org.springframework.web.bind.annotation.GetMapping;
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
        OrderQueryAppService orderQueryAppService
) {

    @GetMapping("/count")
    public Long count() {
        return orderQueryAppService.count();
    }

}
