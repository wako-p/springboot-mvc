package jp.wako.demo.springbootmvc.domain.task;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public final class TaskTest {

    @Nested
    class CreateTest {
        @Test
        @DisplayName("引数にタイトルを指定して生成することができ、タイトルにはその値が使用される")
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

        @Test
        @DisplayName("完了/未完了には「false」が設定される")
        void test3() {
            // when:
            var task = Task.create("test1");

            // then:
            assertEquals(false, task.isDone());
        }
    }


}
