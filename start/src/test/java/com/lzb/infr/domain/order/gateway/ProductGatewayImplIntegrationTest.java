package com.lzb.infr.domain.order.gateway;

import java.util.Collections;

import com.lzb.BaseIntegrationTest;
import com.lzb.domain.order.gateway.ProductGateway;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

class ProductGatewayImplIntegrationTest extends BaseIntegrationTest {

    @Resource
    private ProductGateway productGateway;

    @Test
    void test_not_empty() {
        assertThrows(Exception.class, () -> productGateway.onSale(Collections.emptySet()));
    }
}