package com.lzb.infr.event.persistence.mapper;

import com.lzb.BaseMapperUnitTest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DomainEventPoMapperUnitTest extends BaseMapperUnitTest {

    @Resource
    private DomainEventPoMapper domainEventPoMapper;

    @Test
    void countOfWaitSend() {
        int count = domainEventPoMapper.countOfWaitSend();
        assertThat(count).isGreaterThanOrEqualTo(0);
    }
}