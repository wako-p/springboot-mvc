package jp.wako.demo.springbootmvc.infra.issues;

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

import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import jp.wako.demo.springbootmvc.usecase.issues.TestIssueFactory;

@SpringBootTest
@Transactional
public class IssueDbRepositoryTest {

    @Nested
    public class FindByIdTest {

        @Autowired
        private IssueRepository repository;

        @Test
        @DisplayName("IDを渡すと、そのIDの課題を取得することができる。")
        public void success1() {
            // given:
            // 検索対象のデータを作成して保存
            var saveIssue = TestIssueFactory.create(
                null,
                LocalDateTime.of(2023, 8, 13, 17, 30, 00),
                LocalDateTime.of(2023, 8, 13, 17, 30, 00));

            var savedIssueId = this.repository.save(saveIssue);

            // when:
            var maybeIssue = this.repository.findById(savedIssueId);
            var foundIssue = maybeIssue.orElseGet(() -> fail());

            // then:
            assertEquals(savedIssueId, foundIssue.getId());
            assertEquals("Issue1", foundIssue.getTitle());
            assertEquals("This is a test issue.", foundIssue.getDescription());
            assertEquals(LocalDateTime.of(2023, 8, 13, 17, 30, 00), foundIssue.getCreatedAt());
            assertEquals(LocalDateTime.of(2023, 8, 13, 17, 30, 00), foundIssue.getUpdatedAt());
            assertEquals(1, foundIssue.getVersion());
        }

        @Test
        @DisplayName("渡したIDの課題が存在しない場合は空のOptionalを返す。")
        public void failure1() {
            // when:
            var maybeIssue = this.repository.findById(1);

            // then:
            assertTrue(maybeIssue.isEmpty());
        }

    }

}
