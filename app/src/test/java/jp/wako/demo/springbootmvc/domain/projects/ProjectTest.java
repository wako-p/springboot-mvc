package jp.wako.demo.springbootmvc.domain.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;

public class ProjectTest {
    
    @Nested
    public class CreateTest {

        static Stream<Arguments> parameterForSuccess1() {
            return Stream.of(
                Arguments.of("ProjectA", "This is a test project."),
                Arguments.of("ProjectA", ""));
        }

        @ParameterizedTest
        @MethodSource("parameterForSuccess1")
        @DisplayName("引数にプロジェクト名と説明を指定して生成することができ、その値が属性として使用される。")
        public void success1(final String name, final String description) {
            // when:
            var project = Project.create(name, description);

            // then:
            assertEquals(name, project.getName());
            assertEquals(description, project.getDescription());
        }

        static Stream<Arguments> parameterForFailure1() {
            return Stream.of(
                Arguments.of(null, "This is a test project."),
                Arguments.of("", "This is a test project."));
        }

        @ParameterizedTest
        @MethodSource("parameterForFailure1")
        @DisplayName("引数のプロジェクト名がnullまたは空文字の場合は例外がスローされる。")
        public void failure1(final String name, final String description) {
            // when/then:
            assertThrows(DomainException.class, () -> {
                Project.create(name, description);
            });
        }

        @Test
        @DisplayName("引数の説明がnullの場合は例外がスローされる。")
        public void failure2() {
            // when/then:
            assertThrows(DomainException.class, () -> {
                Project.create("ProjectA", null);
            });
        }

    }

    @Nested
    public class ReconstructTest {

        @Test
        @DisplayName("引数にID、プロジェクト名、説明などを指定して復元することができ、その値が属性として使用される。")
        public void success1() {
            // when:
            var project = Project.reconstruct(
                1000L,
                "ProjectA",
                "This is a test project.",
                LocalDateTime.of(2023, 9, 2, 18, 00, 00),
                LocalDateTime.of(2023, 9, 2, 18, 00, 00),
                1L);

            // then:
            assertEquals(1000L, project.getId());
            assertEquals("ProjectA", project.getName());
            assertEquals("This is a test project.", project.getDescription());
            assertEquals(LocalDateTime.of(2023, 9, 2, 18, 00).withNano(0), project.getCreatedAt());
            assertEquals(LocalDateTime.of(2023, 9, 2, 18, 00).withNano(0), project.getUpdatedAt());
            assertEquals(1L, project.getVersion());
        }

    }

}
