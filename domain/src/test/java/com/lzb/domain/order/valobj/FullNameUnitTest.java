package com.lzb.domain.order.valobj;

import com.lzb.BaseUnitTest;
import com.lzb.domain.order.aggregation.valobj.FullName;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class FullNameUnitTest extends BaseUnitTest {

    @Test
    @DisplayName("值对象不重写hashcode和equals方法，判断对象一致")
    void should_equals() {
        FullName fullName1 = FullName.of("张", "三");
        FullName fullName2 = FullName.of("张", "三");
        assertThat(fullName1.equals(fullName2)).isTrue();
    }

}