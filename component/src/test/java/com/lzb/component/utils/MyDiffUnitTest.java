package com.lzb.component.utils;

import java.io.Serializable;
import java.math.BigDecimal;

import com.lzb.BaseUnitTest;
import lombok.Data;
import org.javers.core.metamodel.annotation.Id;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class MyDiffUnitTest extends BaseUnitTest {

    @Test
    @DisplayName("测试空对象")
    void test_diff_empty_object() {

        Person p1 = new Person();
        Person p2 = new Person();

        MyDiff<Person> diff = new MyDiff<>(p1, p2);
        assertThat(diff.hasChanges()).isFalse();
    }

    @Test
    @DisplayName("测试整形对比")
    void test_diff_int() {
        Person p1 = new Person();
        p1.setId(1L);
        p1.age = 10;

        Person p2 = new Person();
        p2.setId(1L);
        p1.age = 11;

        MyDiff<Person> diff = new MyDiff<>(p1, p2);
        assertThat(diff.hasChanges()).isTrue();

        p1.age = 10;
        p2.age = 10;
        diff = new MyDiff<>(p1, p2);
        assertThat(diff.hasChanges()).isFalse();
    }

    @Test
    @DisplayName("测试BigDecimal对比")
    void test_diff_bigdecimal() {
        Person p1 = new Person();
        p1.setId(1L);
        p1.money = BigDecimal.ONE;

        Person p2 = new Person();
        p2.setId(1L);
        p1.age = 11;

        MyDiff<Person> diff = new MyDiff<>(p1, p2);
        assertThat(diff.hasChanges()).isTrue();

        p1.age = 10;
        p2.age = 10;
        diff = new MyDiff<>(p1, p2);
        assertThat(diff.hasChanges()).isFalse();
    }


    @Data
    public static class Person implements Serializable {

        @Id
        private Long id;
        private String name;
        private BigDecimal money;
        private Integer age;

    }

}