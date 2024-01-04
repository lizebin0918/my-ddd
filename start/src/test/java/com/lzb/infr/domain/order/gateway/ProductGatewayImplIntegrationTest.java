package com.lzb.infr.domain.order.gateway;

import java.util.Collections;

import com.lzb.BaseIntegrationTest;
import com.lzb.domain.order.repository.OrderProductRepository;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

class ProductGatewayImplIntegrationTest extends BaseIntegrationTest {

    @Resource
    private OrderProductRepository orderProductRepository;

    @Test
    void test_not_empty() {
        assertThrows(Exception.class, () -> orderProductRepository.onSale(Collections.emptySet()));
    }
}