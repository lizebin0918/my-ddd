package com.lzb.adapter.web;

import java.math.BigDecimal;
import java.util.List;

import com.lzb.domain.order.enums.OrderStatus;
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

    private String orderNo;
    private BigDecimal amount;
    private List<String> skuCodes;
    private Status status;

}
