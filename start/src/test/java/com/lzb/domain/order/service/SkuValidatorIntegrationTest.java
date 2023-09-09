package com.lzb.domain.order.service;

import java.util.Set;

import com.lzb.BaseIntegrationTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

class SkuValidatorIntegrationTest extends BaseIntegrationTest {


    @Autowired
    private SkuValidator skuValidator;

    @Test
    @DisplayName("测试isAllOnSale")
    void should_all_sku_is_on_sale() {
        assertDoesNotThrow(() -> skuValidator.assertAllOfSkuIsOnSale(Set.of(1, 2, 3)));
    }
}