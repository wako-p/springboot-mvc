package jp.wako.demo.springbootmvc.domain.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.domain.tasks.Task;

public final class TaskTest {

    @Nested
    class CreateTest {
        @Test
        @DisplayName("引数にタイトルを指定して生成することができ、その値が属性として使用される。また、IDはnull、説明は空文字、バージョンは1となる。")
        void success1() {
            // when:
            var task = Task.create("test1");

            // then:
            assertEquals("test1", task.getTitle());
            assertEquals(null, task.getId());
            assertEquals("", task.getDescription());
            assertEquals(1, task.getVersion());
        }

        @Test
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる。")
        void failure1() {
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
        void success1() {
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
        @DisplayName("引数に指定したタイトルと説明で更新することができる。")
        void success1() {
            // given:
            var task = Task.reconstruct(999, "title", "description", LocalDateTime.of(2023, 07, 23, 10, 00), LocalDateTime.of(2023, 07, 23, 10, 00), 1);

            // when:
            task.update("updated title", "updated description");

            // then:
            assertEquals("updated title", task.getTitle());
            assertEquals("updated description", task.getDescription());
        }


        @Test
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる。")
        void failure1() {
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

        // TODO: 更新日のテスト追加する

    }

}
