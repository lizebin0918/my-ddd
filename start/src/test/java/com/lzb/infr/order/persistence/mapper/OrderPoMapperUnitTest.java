package com.lzb.infr.order.persistence.mapper;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzb.BaseMapperUnitTest;
import com.lzb.infr.domain.order.persistence.mapper.OrderPoMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * <br/>
 * Created on : 2023-09-18 22:31
 * @author mac
 */
class OrderPoMapperUnitTest extends BaseMapperUnitTest {

    @Autowired
    private OrderPoMapper orderPoMapper;

    @Test
    @DisplayName("查询订单表")
    void should_count() {
        assertDoesNotThrow(() -> {
            orderPoMapper.selectCount(Wrappers.emptyWrapper());
        });
    }

}
