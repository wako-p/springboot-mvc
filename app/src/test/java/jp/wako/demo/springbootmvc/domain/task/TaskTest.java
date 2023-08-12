package jp.wako.demo.springbootmvc.domain.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.domain.tasks.Task;

public class TaskTest {

    @Nested
    public class CreateTest {

        @Test
        @DisplayName("引数にタイトルを指定して生成することができ、その値が属性として使用される。")
        public void success1() {
            // when:
            var task = Task.create("title");

            // then:
            assertEquals("title", task.getTitle());
        }

        @Test
        @DisplayName("引数にタイトルを指定して生成することができ、IDはnull、説明は空文字、作成日/更新日は現在の日時、バージョンは1となる。")
        public void success2() {
            // given:
            // LocalDateTime.now()の返り値を固定化する
            var datetime = LocalDateTime.of(2023, 07, 23, 11, 00);
            var mock = Mockito.mockStatic(LocalDateTime.class);
            mock.when(LocalDateTime::now).thenReturn(datetime);

            // when:
            var task = Task.create("title");
            mock.close();

            // then:
            assertEquals(null, task.getId());
            assertEquals("", task.getDescription());
            assertEquals(datetime, task.getCreatedAt());
            assertEquals(datetime, task.getUpdatedAt());
            assertEquals(1, task.getVersion());
        }

        @Test
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる。")
        public void failure1() {
            // when/then:
            assertThrows(DomainException.class, () -> {
                Task.create(null);
            });
            assertThrows(DomainException.class, () -> {
                Task.create("");
            });
        }

    }

    @Nested
    class ReconstructTest {
        @Test
        @DisplayName("引数にID、タイトル、説明などを指定して復元することができ、その値が属性として使用される。")
        public void success1() {
            // when:
            var task = Task.reconstruct(999, "Task1", "This is test description.", LocalDateTime.of(2023, 07, 23, 10, 00), LocalDateTime.of(2023, 07, 23, 10, 00), 1);

            // then:
            assertEquals(999, task.getId());
            assertEquals("Task1", task.getTitle());
            assertEquals("This is test description.", task.getDescription());
            assertEquals(LocalDateTime.of(2023, 07, 23, 10, 00), task.getCreatedAt());
            assertEquals(LocalDateTime.of(2023, 07, 23, 10, 00), task.getUpdatedAt());
            assertEquals(1, task.getVersion());
        }
    }

    @Nested
    class UpdateTest {

        @Test
        @DisplayName("引数に指定したタイトルと説明で更新することができる。また更新日はそのときの日時となる。")
        public void success1() {
            // given:
            // LocalDateTime.now()の返り値を固定化する
            var datetime = LocalDateTime.of(2023, 07, 23, 11, 00);
            var mock = Mockito.mockStatic(LocalDateTime.class);
            mock.when(LocalDateTime::now).thenReturn(datetime);

            var task = Task.reconstruct(
                999,
                "title",
                "description",
                LocalDateTime.of(2023, 07, 23, 10, 00),
                LocalDateTime.of(2023, 07, 23, 10, 00), 
                1);

            // when:
            task.update("updated title", "updated description");
            mock.close();

            // then:
            assertEquals("updated title", task.getTitle());
            assertEquals("updated description", task.getDescription());
            assertEquals(LocalDateTime.of(2023, 07, 23, 11, 00), task.getUpdatedAt());
        }


        @Test
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる。")
        public void failure1() {
            // given:
            var task = Task.reconstruct(999, "title", "description", LocalDateTime.of(2023, 07, 23, 10, 00), LocalDateTime.of(2023, 07, 23, 10, 00), 1);

            // when/then:
            assertThrows(DomainException.class, () -> {
                task.update(null, "description");
            });
            assertThrows(DomainException.class, () -> {
                task.update("", "description");
            });
        }

    }

}
