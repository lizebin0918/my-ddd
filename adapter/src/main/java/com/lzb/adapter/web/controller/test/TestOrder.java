package com.lzb.adapter.web.controller.test;

import java.math.BigDecimal;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * <br/>
 * Created on : 2023-09-10 15:34
 * @author lizebin
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TestOrder {

    @NotBlank(message = "订单号不能为空串")
    private String orderNo;
    private BigDecimal amount;
    private List<String> skuCodes;
    private Status status;

}
