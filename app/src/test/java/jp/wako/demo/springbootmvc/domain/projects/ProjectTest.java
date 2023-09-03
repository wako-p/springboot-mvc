package jp.wako.demo.springbootmvc.domain.projects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
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

        @Test
        @DisplayName("プロジェクトの課題は0件になっている。")
        public void success2() {
            // when:
            var project = Project.create("ProjectA", "This is a test project.");

            // then:
            assertEquals(0, project.issues.size());
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
                1000,
                "ProjectA",
                "This is a test project.",
                new ArrayList<>(),
                LocalDateTime.of(2023, 9, 2, 18, 00, 00),
                LocalDateTime.of(2023, 9, 2, 18, 00, 00),
                1);

            // then:
            assertEquals(1000, project.getId());
            assertEquals("ProjectA", project.getName());
            assertEquals("This is a test project.", project.getDescription());
            assertEquals(LocalDateTime.of(2023, 9, 2, 18, 00).withNano(0), project.getCreatedAt());
            assertEquals(LocalDateTime.of(2023, 9, 2, 18, 00).withNano(0), project.getUpdatedAt());
            assertEquals(1, project.getVersion());
        }

    }

    @Nested
    public class AddTest {

        @Test
        @DisplayName("課題を追加することができる")
        public void success1() {
            // given:
            var project = Project.create("ProjectA", "This is a test project.");

            var issue1 = Issue.reconstruct(1000, 1000, "IssueA", "This is a test issue.", LocalDateTime.of(2023, 9, 2, 20, 10, 00), LocalDateTime.of(2023, 9, 2, 20, 10, 00), 1);
            var issue2 = Issue.reconstruct(1001, 1000, "IssueB", "This is a test issue.", LocalDateTime.of(2023, 9, 2, 20, 20, 00), LocalDateTime.of(2023, 9, 2, 20, 20, 00), 1);
            var issue3 = Issue.reconstruct(1002, 1000, "IssueC", "This is a test issue.", LocalDateTime.of(2023, 9, 2, 20, 30, 00), LocalDateTime.of(2023, 9, 2, 20, 30, 00), 1);

            // when:
            project.add(issue1);
            project.add(issue2);
            project.add(issue3);

            // then:
            assertEquals(3, project.issues.size());
        }

    }

}
