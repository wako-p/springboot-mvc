package jp.wako.demo.springbootmvc.usecase.issues.create;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

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
import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;

public class IssueCreateUseCaseTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class ExecuteTest {

        @Mock
        private IssueRepository repository;

        @InjectMocks
        private IssueCreateUseCase usecase;

        @Captor
        private ArgumentCaptor<Issue> issueCaptor;

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるタイトルを使用して、新規作成された課題が保存される。")
        public void success1() {
            // when:
            var request = new IssueCreateRequest("Issue1");
            this.usecase.execute(request);

            // then:
            // save()に渡された課題のアサーション
            verify(this.repository).save(issueCaptor.capture());
            var capturedIssue = issueCaptor.getValue();

            // DTOに含まれるタイトルを使用して課題が新規作成されている
            assertEquals("Issue1", capturedIssue.getTitle());
        }

    }

}
