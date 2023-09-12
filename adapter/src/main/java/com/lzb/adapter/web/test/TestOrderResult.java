package com.lzb.adapter.web.test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

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

    @Builder.Default
    private final List<String> list = new ArrayList<>();

}
