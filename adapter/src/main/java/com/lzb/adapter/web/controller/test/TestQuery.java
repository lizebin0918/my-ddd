package com.lzb.adapter.web.controller.test;

import java.math.BigDecimal;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-13 19:47
 *
 * @author lizebin
 */
@Data
public class TestQuery {

    private int uid;
    private List<Integer> ids;
    private BigDecimal amount;

}
