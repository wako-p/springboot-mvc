package jp.wako.demo.springbootmvc.infra.issues.sort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class ColumnTest {

    @Nested
    public class ParseByTest {

        static Stream<Arguments> parameterForSuccess1() {
            return Stream.of(
                Arguments.of("id", Column.ID),
                Arguments.of("title", Column.TITLE),
                Arguments.of("description", Column.DESCRIPTION),
                Arguments.of("updated_at", Column.UPDATEDAT));
            }

        @ParameterizedTest
        @MethodSource("parameterForSuccess1")
        @DisplayName("引数に定義済み列名(文字列)を指定するとColumnに変換できる")
        public void success1(final String column, final Column expected) {
            // when:
            var actual = Column.parseBy(column);

            // then:
            assertEquals(expected, actual);
        }

        static Stream<Arguments> parameterForFailure1() {
            return Stream.of(
                Arguments.of(""),
                Arguments.of("column"));
        }

        @ParameterizedTest
        @MethodSource("parameterForFailure1")
        @DisplayName("引数に未定義の列名(文字列)を指定すると例外がスローされる")
        public void failure1(final String statement) {
            // when/then:
            assertThrows(IllegalArgumentException.class, () -> {
                Column.parseBy(statement);
            });
        }

    }

}
