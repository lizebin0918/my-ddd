package com.lzb.component.utils.json;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;

import com.lzb.BaseUnitTest;
import lombok.Getter;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JsonUtilsUnitTest extends BaseUnitTest {

    @Test
    @Disabled
    @DisplayName("测试时间序列化")
    void should_test_date_to_json() {
        System.out.println(new A());
    }

    @Getter
    static class A {
        LocalDateTime localDateTime = LocalDateTime.now();
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        Instant timestamp = Instant.now();
        OffsetDateTime offsetDateTime = OffsetDateTime.now();
    }

}