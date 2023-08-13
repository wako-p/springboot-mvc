package jp.wako.demo.springbootmvc.infra.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.usecase.tasks.TestTaskFactory;

@SpringBootTest
@Transactional
public class DbTaskRepositoryTest {

    @Nested
    public class FindByIdTest {

        @Autowired
        private TaskRepository repository;

        @Test
        @DisplayName("IDを渡すと、そのIDのタスクを取得することができる。")
        public void success1() {
            // given:
            // 検索対象のデータを作成して保存
            // NOTE: なんかof()で生成するとテスト失敗するので文字列から生成する
            var createdAt = LocalDateTime.parse("2023/08/13 17:00", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
            var updatedAt = LocalDateTime.parse("2023/08/13 17:00", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"));
            var createdTask = TestTaskFactory.create(null, createdAt, updatedAt);
            var createdTaskId = this.repository.save(createdTask);

            // when:
            var maybeTask = this.repository.findById(createdTaskId);
            var foundTask = maybeTask.orElseGet(() -> fail());

            // then:
            assertEquals(createdTaskId, foundTask.getId());
            assertEquals("title", foundTask.getTitle());
            assertEquals("description", foundTask.getDescription());
            assertEquals(createdAt, foundTask.getCreatedAt());
            assertEquals(updatedAt, foundTask.getUpdatedAt());
            assertEquals(1, foundTask.getVersion());
        }

        @Test
        @DisplayName("渡したIDのタスクが存在しない場合は空のOptionalを返す。")
        public void failure1() {
            // when:
            var maybeTask = this.repository.findById(1);

            // then:
            assertTrue(maybeTask.isEmpty());
        }

    }

}
