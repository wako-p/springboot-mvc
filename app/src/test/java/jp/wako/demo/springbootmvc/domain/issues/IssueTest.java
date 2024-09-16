package jp.wako.demo.springbootmvc.domain.issues;

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
import org.mockito.Mockito;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;

public class IssueTest {

    @Nested
    public class CreateTest {

        @Test
        @DisplayName("引数にプロジェクトIDとタイトル、説明を指定して生成することができ、その値が属性として使用される。")
        public void success1() {
            // when:
            var issue = Issue.create(1000L, "Issue1", "This is a test issue.");

            // then:
            assertEquals(1000, issue.getProjectId());
            assertEquals("Issue1", issue.getTitle());
            assertEquals("This is a test issue.", issue.getDescription());
        }

        @Test
        @DisplayName("引数にプロジェクトIDとタイトル、説明を指定して生成することができ、IDはnull、作成日/更新日は現在の日時、バージョンは1となる。")
        public void success2() {
            // given:
            // LocalDateTime.now()の返り値を固定化する
            var datetime = LocalDateTime.of(2023, 07, 23, 11, 00, 00);
            var mock = Mockito.mockStatic(LocalDateTime.class);
            mock.when(LocalDateTime::now).thenReturn(datetime);

            // when:
            var issue = Issue.create(1000L, "Issue1", "This is a test issue.");
            mock.close();

            // then:
            assertEquals(null, issue.getId());
            assertEquals(1000L, issue.getProjectId());
            assertEquals("Issue1", issue.getTitle());
            assertEquals("This is a test issue.", issue.getDescription());
            assertEquals(datetime, issue.getCreatedAt());
            assertEquals(datetime, issue.getUpdatedAt());
            assertEquals(1L, issue.getVersion());
        }

        @Test
        @DisplayName("引数に指定したプロジェクトIDがnullの場合は例外がスローされる。")
        public void failure1() {
            // when/then:
            assertThrows(DomainException.class, () -> {
                Issue.create(null, "Issue1", "This is a test issue.");
            });
        }

        static Stream<Arguments> parameterForFailure2() {
            return Stream.of(
                Arguments.of(1000L, null, "This is a test issue."),
                Arguments.of(1000L, "", "This is a test issue."));
        }

        @ParameterizedTest
        @MethodSource("parameterForFailure2")
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる。")
        public void failure2(final Long projectId,final String title, final String description) {
            // when/then:
            assertThrows(DomainException.class, () -> {
                Issue.create(projectId, title, description);
            });
        }

        @Test
        @DisplayName("引数に指定した説明がnullの場合は例外がスローされる。")
        public void failure3() {
            // when/then:
            assertThrows(DomainException.class, () -> {
                Issue.create(1000L, "Issue1", null);
            });
        }

    }

    @Nested
    class ReconstructTest {
        @Test
        @DisplayName("引数にID、タイトル、説明などを指定して復元することができ、その値が属性として使用される。")
        public void success1() {
            // when:
            var issue = Issue.reconstruct(
                1000L,
                1000L,
                "Issue1",
                "This is a test issue.",
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                1L);

            // then:
            assertEquals(1000L, issue.getId());
            assertEquals(1000L, issue.getProjectId());
            assertEquals("Issue1", issue.getTitle());
            assertEquals("This is a test issue.", issue.getDescription());
            assertEquals(LocalDateTime.of(2023, 07, 23, 10, 00, 00), issue.getCreatedAt());
            assertEquals(LocalDateTime.of(2023, 07, 23, 10, 00, 00), issue.getUpdatedAt());
            assertEquals(1L, issue.getVersion());
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("引数に指定したタイトルと説明で更新することができる。また更新日はそのときの日時となる。")
        public void success1() {
            // given:
            // LocalDateTime.now()の返り値を固定化する
            var datetime = LocalDateTime.of(2023, 07, 23, 11, 00, 00);
            var mock = Mockito.mockStatic(LocalDateTime.class);
            mock.when(LocalDateTime::now).thenReturn(datetime);

            var issue = Issue.reconstruct(
                1000L,
                1000L,
                "Issue1",
                "This is a test issue.",
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                1L);

            // when:
            issue.update("Updated issue1", "This is a updated test issue.");
            mock.close();

            // then:
            assertEquals("Updated issue1", issue.getTitle());
            assertEquals("This is a updated test issue.", issue.getDescription());
            assertEquals(LocalDateTime.of(2023, 07, 23, 11, 00, 00), issue.getUpdatedAt());
        }

        static Stream<Arguments> parameterForFailure1() {
            return Stream.of(
                Arguments.of(null, "This is a test issue."),
                Arguments.of("", "This is a test issue."));
        }

        @ParameterizedTest
        @MethodSource("parameterForFailure1")
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる。")
        public void failure1(final String title, final String description) {
            // given:
            var issue = Issue.reconstruct(
                1000L,
                1000L,
                "Issue1",
                "This is a test issue.",
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                1L);

            // when/then:
            assertThrows(DomainException.class, () -> {
                issue.update(title, description);
            });
        }

        @Test
        @DisplayName("引数に指定した説明がnullの場合は例外がスローされる。")
        public void failure1() {
            // given:
            var issue = Issue.reconstruct(
                1000L,
                1000L,
                "Issue1",
                "This is a test issue.",
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                1L);

            // when/then:
            assertThrows(DomainException.class, () -> {
                issue.update("Issue1", null);
            });
        }

    }

}
