package jp.wako.demo.springbootmvc.domain.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.wako.demo.springbootmvc.domain.tasks.Task;

public final class TaskTest {

    @Nested
    class CreateTest {
        @Test
        @DisplayName("引数にタイトルを指定して生成することができ、その値が属性として使用される")
        void test1() {
            // when:
            var task = Task.create("test1");

            // then:
            assertEquals("test1", task.getTitle());
        }

        @Test
        @DisplayName("説明には「No description provided.」が設定される")
        void test2() {
            // when:
            var task = Task.create("test1");

            // then:
            assertEquals("No description provided.", task.getDescription());
        }

    }

    @Nested
    class ReconstructTest {
        @Test
        @DisplayName("引数にID、タイトル、説明などを指定して復元することができ、その値が属性として使用される")
        void test1() {
            // when:
            var task = Task.reconstruct("999", "Task1", "This is test description.", LocalDateTime.of(2023, 07, 23, 10, 00));

            // then:
            assertEquals("999", task.getId());
            assertEquals("Task1", task.getTitle());
            assertEquals("This is test description.", task.getDescription());
            assertEquals(LocalDateTime.of(2023, 07, 23, 10, 00), task.getCreateAt());
        }
    }
}
