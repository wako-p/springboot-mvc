package jp.wako.demo.springbootmvc.usecase.issues.update;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import jp.wako.demo.springbootmvc.usecase.issues.TestIssueFactory;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;

public class IssueUpdateUseCaseTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class ExecuteTest {

        @Mock
        private IIssueRepository repository;

        @InjectMocks
        private IssueUpdateUseCase usecase;

        @Captor
        private ArgumentCaptor<Long> issueIdCaptor;

        @Captor
        private ArgumentCaptor<Issue> issueCaptor;

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるIDの課題が取得されている。")
        public void success1() {
            // given:
            when(this.repository.findById(anyLong()))
                .thenReturn(Optional.of(TestIssueFactory.create(1000L)));

            // when:
            var request = new IssueUpdateRequest(1000L, 1000L, "Issue1", "This is a test issue.");
            this.usecase.execute(request);

            // then:
            // 渡されたDTOに含まれるIDの課題を取得している
            verify(this.repository).findById(issueIdCaptor.capture());
            var capturedIssueId = issueIdCaptor.getValue();
            assertEquals(1000L, capturedIssueId);
        }

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるタイトルと説明で更新された課題が保存される。")
        public void success2() {
            // given:
            when(this.repository.findById(anyLong()))
                .thenReturn(Optional.of(TestIssueFactory.create(1000L)));

            // when:
            var request = new IssueUpdateRequest(1000L, 1000L, "Issue1", "This is a test issue.");
            this.usecase.execute(request);

            // then:
            // save()に渡された課題のアサーション
            verify(this.repository).save(issueCaptor.capture());
            var capturedIssue = issueCaptor.getValue();

            assertEquals(1000L, capturedIssue.getId());
            assertEquals("Issue1", capturedIssue.getTitle());
            assertEquals("This is a test issue.", capturedIssue.getDescription());
        }

        @Test
        @DisplayName("DTOに含まれるIDの課題が存在しない場合は例外がスローされる。")
        public void failure1() {
            // given:
            when(this.repository.findById(anyLong()))
                .thenReturn(Optional.empty());

            // when/then:
            var request = new IssueUpdateRequest(1000L, 1000L, "Issue1", "This is a test issue.");
            assertThrows(UseCaseException.class, () -> {
                this.usecase.execute(request);
            });
        }

    }
}
