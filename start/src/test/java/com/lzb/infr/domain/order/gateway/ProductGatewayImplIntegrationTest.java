package com.lzb.infr.domain.order.gateway;

import java.util.Collections;

import com.lzb.BaseIntegrationTest;
import com.lzb.domain.order.query.ProductQuery;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

class ProductGatewayImplIntegrationTest extends BaseIntegrationTest {

    @Resource
    private ProductQuery productQuery;

    @Test
    void test_not_empty() {
        assertThrows(Exception.class, () -> productQuery.onSale(Collections.emptySet()));
    }
}