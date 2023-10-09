package com.lzb.test;

import java.util.stream.Stream;

import com.lzb.BaseUnitTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 * 加法计算器<br/>
 * Created on : 2023-10-09 08:51
 * @author mac
 */
public class CalculatorUnitTest extends BaseUnitTest {

    class Calculator {

            public int add(int a, int b) {
                return a + b;
            }

    }

    /*@CsvSource({
            "1, 2, 3",
            "2, 3, 5",
            "3, 4, 7"
    })*/
    @ParameterizedTest(name = "{0} + {1} = {2}")
    @DisplayName("加法计算器测试")
    @MethodSource
    void test_add(int i, int j, int result) {
        Calculator calculator = new Calculator();
        assertThat(calculator.add(i, j)).isEqualTo(result);
    }

    public static Stream<Arguments> test_add() {
        return Stream.of(
                Arguments.of(Named.of("加数:1", 1), Named.of("加数:2", 2), Named.of("加数:3", 3)),
                Arguments.of(Named.of("加数:2", 2), Named.of("加数:3", 3), Named.of("加数:5", 5))
        );
    }

}
