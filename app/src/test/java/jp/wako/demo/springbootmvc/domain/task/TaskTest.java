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
        @DisplayName("引数にタイトルを指定して生成することができ、その値が属性として使用され説明は空文字となる")
        void success1() {
            // when:
            var task = Task.create("test1");

            // then:
            assertEquals("test1", task.getTitle());
            assertEquals("", task.getDescription());
        }

        @Test
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる")
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
        @DisplayName("引数にID、タイトル、説明などを指定して復元することができ、その値が属性として使用される")
        void success1() {
            // when:
            var task = Task.reconstruct(999, "Task1", "This is test description.", LocalDateTime.of(2023, 07, 23, 10, 00), 1);

            // then:
            assertEquals(999, task.getId());
            assertEquals("Task1", task.getTitle());
            assertEquals("This is test description.", task.getDescription());
            assertEquals(LocalDateTime.of(2023, 07, 23, 10, 00), task.getCreateAt());
        }
    }

    @Nested
    class UpdateTitleTest {

        @Test
        @DisplayName("引数に指定したタイトルに更新することができる")
        void success1() {
            // given:
            var task = Task.reconstruct(999, "Task1", "This is test description.", LocalDateTime.of(2023, 07, 23, 10, 00), 1);

            // when:
            task.updateTitle("Task2");

            // then:
            assertEquals("Task2", task.getTitle());
        }

        @Test
        @DisplayName("引数に指定したタイトルがnullまたは空文字の場合は例外がスローされる")
        void failure1() {
            // given:
            var task = Task.reconstruct(999, "Task1", "This is test description.", LocalDateTime.of(2023, 07, 23, 10, 00), 1);

            // when/then:
            assertThrows(DomainException.class, () -> {
                task.updateTitle(null);
            });
            assertThrows(DomainException.class, () -> {
                task.updateTitle("");
            });
        }

    }

    @Nested
    class UpdateDescriptionTest {
        @Test
        @DisplayName("引数に指定した説明に更新することができる")
        void success1() {
            // given:
            var task = Task.reconstruct(999, "Task1", "This is test description.", LocalDateTime.of(2023, 07, 23, 10, 00), 1);

            // when:
            task.updateDescription("updated description.");

            // then:
            assertEquals("updated description.", task.getDescription());
        }
    }
}
