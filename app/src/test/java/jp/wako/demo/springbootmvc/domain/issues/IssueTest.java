package jp.wako.demo.springbootmvc.domain.issues;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;

public class IssueTest {

    @Nested
    public class CreateTest {

        @Test
        @DisplayName("引数にタイトルを指定して生成することができ、その値が属性として使用される。")
        public void success1() {
            // when:
            var issue = Issue.create("Issue1");

            // then:
            assertEquals("Issue1", issue.getTitle());
        }

        @Test
        @DisplayName("引数にタイトルを指定して生成することができ、IDはnull、説明は空文字、作成日/更新日は現在の日時、バージョンは1となる。")
        public void success2() {
            // given:
            // LocalDateTime.now()の返り値を固定化する
            var datetime = LocalDateTime.of(2023, 07, 23, 11, 00, 00);
            var mock = Mockito.mockStatic(LocalDateTime.class);
            mock.when(LocalDateTime::now).thenReturn(datetime);

            // when:
            var issue = Issue.create("title");
            mock.close();

            // then:
            assertEquals(null, issue.getId());
            assertEquals("", issue.getDescription());
            assertEquals(datetime, issue.getCreatedAt());
            assertEquals(datetime, issue.getUpdatedAt());
            assertEquals(1, issue.getVersion());
        }

        @Test
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる。")
        public void failure1() {
            // when/then:
            assertThrows(DomainException.class, () -> {
                Issue.create(null);
            });
            assertThrows(DomainException.class, () -> {
                Issue.create("");
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
                1000, 
                "Issue1", 
                "This is a test issue.",
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                1);

            // then:
            assertEquals(1000, issue.getId());
            assertEquals("Issue1", issue.getTitle());
            assertEquals("This is a test issue.", issue.getDescription());
            assertEquals(LocalDateTime.of(2023, 07, 23, 10, 00, 00), issue.getCreatedAt());
            assertEquals(LocalDateTime.of(2023, 07, 23, 10, 00, 00), issue.getUpdatedAt());
            assertEquals(1, issue.getVersion());
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
                999,
                "Issue1",
                "This is a test issue.",
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                1);

            // when:
            issue.update("Updated issue1", "This is a updated test issue.");
            mock.close();

            // then:
            assertEquals("Updated issue1", issue.getTitle());
            assertEquals("This is a updated test issue.", issue.getDescription());
            assertEquals(LocalDateTime.of(2023, 07, 23, 11, 00, 00), issue.getUpdatedAt());
        }

        @Test
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる。")
        public void failure1() {
            // given:
            var issue = Issue.reconstruct(
                999,
                "Issue1",
                "This is a test issue.",
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                LocalDateTime.of(2023, 07, 23, 10, 00, 00),
                1);

            // when/then:
            assertThrows(DomainException.class, () -> {
                issue.update(null, "This is a test issue.");
            });
            assertThrows(DomainException.class, () -> {
                issue.update("", "This is a test issue.");
            });
        }

    }

}
