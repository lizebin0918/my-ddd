package com.lzb.adapter.web;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-09-10 15:38
 * @author lizebin
 */
@Getter
@AllArgsConstructor
@Builder
public class TestOrderResult {

    private final Status status;
    private final BigDecimal amount;
    private final OffsetDateTime offsetDateTime;
    private final LocalDateTime localDateTime;

}
