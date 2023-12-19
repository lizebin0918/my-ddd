package com.lzb.adapter.web.test;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <br/>
 * Created on : 2023-10-14 08:29
 * @author mac
 */
@Getter
@AllArgsConstructor
public class Height {

    private int value;
    private Unit unit;

    enum Unit {
        CM, M;
    }

}
