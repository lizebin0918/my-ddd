package com.lzb.adapter.web;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * <br/>
 * Created on : 2023-09-10 15:34
 * @author lizebin
 */
public record TestOrder(
        String orderNo,
        BigDecimal amount,
        List<String> skuCodes,
        Status status) {

}
