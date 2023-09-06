package jp.wako.demo.springbootmvc.infra.issues.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jp.wako.demo.springbootmvc.infra.shared.sort.Order;

public class SortTest {

    @Nested
    public class ToStringTest {

        static Stream<Arguments> parameterForSuccess1() {
            return Stream.of(
                Arguments.of(Column.ID, Order.ASC, "id asc"),
                Arguments.of(Column.ID, Order.DESC, "id desc"),
                Arguments.of(Column.TITLE, Order.ASC, "title asc"),
                Arguments.of(Column.TITLE, Order.DESC, "title desc"),
                Arguments.of(Column.UPDATEDAT, Order.ASC, "updated_at asc"),
                Arguments.of(Column.UPDATEDAT, Order.DESC, "updated_at desc"));
            }

        @ParameterizedTest
        @MethodSource("parameterForSuccess1")
        @DisplayName("インスタンス生成時に指定したColumnとOrderによって <column> <order> な形式の文字列を返すことができる")
        public void success1(final Column column, final Order order, final String expected) {
            // given:
            var sort = new Sort(column, order);

            // when:
            var actual = sort.toString();

            // then:
            assertEquals(expected, actual);
        }

    }
}
