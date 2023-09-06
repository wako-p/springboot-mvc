package jp.wako.demo.springbootmvc.infra.shared.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class OrderTest {

    @Nested
    public class ParseByTest {

        static Stream<Arguments> parameterForSuccess1() {
            return Stream.of(
                Arguments.of("asc", Order.ASC),
                Arguments.of("desc", Order.DESC));
        }

        @ParameterizedTest
        @MethodSource("parameterForSuccess1")
        @DisplayName("引数に定義済みのステートメント(文字列)を指定するとOrderに変換できる")
        public void success1(final String statement, final Order expected) {
            // when:
            var actual = Order.parseBy(statement);

            // then:
            assertEquals(expected, actual);
        }

        static Stream<Arguments> parameterForFailure1() {
            return Stream.of(
                Arguments.of(""),
                Arguments.of("abc"),
                Arguments.of("dec"));
        }

        @ParameterizedTest
        @MethodSource("parameterForFailure1")
        @DisplayName("引数に未定義のステートメント(文字列)を指定すると例外がスローされる")
        public void failure1(final String statement) {
            // when/then:
            assertThrows(IllegalArgumentException.class, () -> {
                Order.parseBy(statement);
            });
        }

    }

}
