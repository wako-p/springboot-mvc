package jp.wako.demo.springbootmvc.usecase.issues.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
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

import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import jp.wako.demo.springbootmvc.usecase.issues.TestIssueFactory;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;

public class GetIssueUseCaseTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class ExecuteTest {

        @Mock
        private IssueRepository repository;

        @InjectMocks
        private GetIssueUseCase usecase;

        @Captor
        private ArgumentCaptor<Integer> issueIdCaptor;

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるIDの課題が取得され、DTOに詰め替えて返される。")
        public void success1() {
            // given:
            when(this.repository.findById(anyInt()))
                .thenReturn(Optional.of(TestIssueFactory.create(1000)));

            // when:
            var request = new GetIssueRequest(1000);
            var actual = this.usecase.execute(request);

            // then:
            // 渡されたDTOに含まれるIDの課題を取得している
            verify(this.repository).findById(issueIdCaptor.capture());
            var capturedIssueId = issueIdCaptor.getValue();
            assertEquals(1000, capturedIssueId);

            // 取得した課題をDTOに詰め替えて返している
            var expected = new GetIssueResponse(1000, "Issue1", "This is a test issue.", 1);
            assertEquals(expected, actual);
        }

        @Test
        @DisplayName("DTOに含まれるIDの課題が存在しない場合は例外がスローされる。")
        public void failure1() {
            // given:
            when(this.repository.findById(anyInt()))
                .thenReturn(Optional.empty());

            // when/then:
            var request = new GetIssueRequest(100);
            assertThrows(UseCaseException.class, () -> {
                this.usecase.execute(request);
            });
        }

    }

}
