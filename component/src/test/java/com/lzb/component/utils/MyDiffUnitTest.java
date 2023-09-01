package com.lzb.component.utils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
        p2.money = BigDecimal.TEN;

        MyDiff<Person> diff = new MyDiff<>(p1, p2);
        assertThat(diff.hasChanges()).isTrue();

        p1.money = new BigDecimal("88");
        p2.money = new BigDecimal("88");
        diff = new MyDiff<>(p1, p2);
        assertThat(diff.hasChanges()).isFalse();
    }

    @Test
    @DisplayName("对比列表")
    void should_diff_list() {

        List<Person> oldPersons = new ArrayList<>();
        Person p1 = new Person();
        p1.setId(1L);
        p1.money = BigDecimal.ONE;
        oldPersons.add(p1);
        Person p2 = new Person();
        p2.setId(2L);
        p2.money = BigDecimal.TEN;
        oldPersons.add(p2);

        List<Person> newPersons = new ArrayList<>();
        Person p3 = new Person();
        p3.setId(1L);
        p3.money = new BigDecimal("2");
        newPersons.add(p3);
        Person p4 = new Person();
        p4.setId(2L);
        p4.money = new BigDecimal("11");
        newPersons.add(p4);
        Person person5 = new Person();
        person5.setId(3L);
        person5.setName("person5");
        newPersons.add(person5);

        List<Person> adds = new ArrayList<>();
        List<Person> updates = new ArrayList<>();
        List<Person> removes = new ArrayList<>();
        MyDiff.listChangeFunction(oldPersons, newPersons, Person.class, adds::addAll, updates::addAll, removes::addAll);

        assertThat(adds).hasSize(1);
        assertThat(updates).hasSize(2);
        assertJSON(updates);
        assertThat(removes).hasSize(0);

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