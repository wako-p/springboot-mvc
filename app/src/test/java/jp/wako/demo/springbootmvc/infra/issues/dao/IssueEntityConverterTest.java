package jp.wako.demo.springbootmvc.infra.issues.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import jp.wako.demo.springbootmvc.domain.issues.Issue;

public class IssueEntityConverterTest {

    @Nested
    public class ToDomainTest {

        @Test
        @DisplayName("引数にEntityを渡すとDomainオブジェクトに変換できる")
        public void success1() {
            // given:
            var converter = new IssueEntityConverter();
            var entity = new IssueEntity(
                1000,
                1000,
                "Issue1",
                "This is a test issue.",
                LocalDateTime.of(2023, 9, 3, 14, 00, 00),
                LocalDateTime.of(2023, 9, 3, 14, 00, 00),
                1);

            // when:
            var domain = converter.toDomain(entity);

            // then:
            assertEquals(1000, domain.getId());
            assertEquals(1000, domain.getProjectId());
            assertEquals("Issue1", domain.getTitle());
            assertEquals("This is a test issue.", domain.getDescription());
            assertEquals(LocalDateTime.of(2023, 9, 3, 14, 00, 00), domain.getCreatedAt());
            assertEquals(LocalDateTime.of(2023, 9, 3, 14, 00, 00), domain.getUpdatedAt());
            assertEquals(1, domain.getVersion());
        }

    }

    @Nested
    public class ToEntityTest {

        @Test
        @DisplayName("引数にDomainオブジェクトを渡すとEntityに変換できる")
        public void success1() {
            // given:
            var converter = new IssueEntityConverter();
            var domain = Issue.reconstruct(
                1000,
                1000,
                "Issue1",
                "This is a test issue.",
                LocalDateTime.of(2023, 9, 3, 14, 00, 00),
                LocalDateTime.of(2023, 9, 3, 14, 00, 00),
                1);

            // when:
            var entity = converter.toEntity(domain);

            // then:
            assertEquals(1000, entity.getId());
            assertEquals(1000, entity.getProjectId());
            assertEquals("Issue1", entity.getTitle());
            assertEquals("This is a test issue.", entity.getDescription());
            assertEquals(LocalDateTime.of(2023, 9, 3, 14, 00, 00), entity.getCreatedAt());
            assertEquals(LocalDateTime.of(2023, 9, 3, 14, 00, 00), entity.getUpdatedAt());
            assertEquals(1, entity.getVersion());
        }

    }
}
