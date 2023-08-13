package jp.wako.demo.springbootmvc.infra.tasks;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

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
            var saveTask = TestTaskFactory.create(
                null,
                LocalDateTime.of(2023, 8, 13, 17, 30).withNano(0),
                LocalDateTime.of(2023, 8, 13, 17, 30).withNano(0));

            var savedTaskId = this.repository.save(saveTask);

            // when:
            var maybeTask = this.repository.findById(savedTaskId);
            var foundTask = maybeTask.orElseGet(() -> fail());

            // then:
            assertEquals(savedTaskId, foundTask.getId());
            assertEquals("title", foundTask.getTitle());
            assertEquals("description", foundTask.getDescription());
            assertEquals(LocalDateTime.of(2023, 8, 13, 17, 30).withNano(0), foundTask.getCreatedAt());
            assertEquals(LocalDateTime.of(2023, 8, 13, 17, 30).withNano(0), foundTask.getUpdatedAt());
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
