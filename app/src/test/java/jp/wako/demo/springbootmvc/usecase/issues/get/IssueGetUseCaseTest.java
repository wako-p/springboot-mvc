package jp.wako.demo.springbootmvc.usecase.issues.get;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
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

import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import jp.wako.demo.springbootmvc.domain.projects.IProjectRepository;
import jp.wako.demo.springbootmvc.domain.projects.Project;
import jp.wako.demo.springbootmvc.usecase.issues.TestIssueFactory;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;

public class IssueGetUseCaseTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class ExecuteTest {

        @Mock
        private IProjectRepository projectRepository;

        @Mock
        private IIssueRepository issueRepository;

        @InjectMocks
        private IssueGetUseCase usecase;

        @Captor
        private ArgumentCaptor<Long> issueIdCaptor;

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるIDの課題が取得され、DTOに詰め替えて返される。")
        public void success1() {
            // given:
            when(this.projectRepository.findById(anyLong()))
                .thenReturn(Optional.of(Project.reconstruct(
                    1000L, 
                    "ProjectA", 
                    "This is a test project.",
                    LocalDateTime.now().withNano(0),
                    LocalDateTime.now().withNano(0),
                    1L)));

            when(this.issueRepository.findById(anyLong()))
                .thenReturn(Optional.of(TestIssueFactory.create(1000L)));

            // when:
            var request = new IssueGetRequest(1000L, 1000L);
            var actual = this.usecase.execute(request);

            // then:
            // 渡されたDTOに含まれるIDの課題を取得している
            verify(this.issueRepository).findById(issueIdCaptor.capture());
            var capturedIssueId = issueIdCaptor.getValue();
            assertEquals(1000, capturedIssueId);

            // 取得した課題をDTOに詰め替えて返している
            var projectDto = new ProjectDto(1000L, "ProjectA");
            var issueDto = new IssueDto(1000L, "Issue1", "This is a test issue.");
            var expected = new IssueGetResponse(projectDto, issueDto);
            assertEquals(expected, actual);
        }

        // TODO: プロジェクトが見つからない場合のテストも書く

        @Test
        @DisplayName("DTOに含まれるIDの課題が存在しない場合は例外がスローされる。")
        public void failure1() {
            // given:
            when(this.projectRepository.findById(anyLong()))
                .thenReturn(Optional.of(Project.reconstruct(
                    1000L, 
                    "ProjectA", 
                    "This is a test project.",
                    LocalDateTime.now().withNano(0),
                    LocalDateTime.now().withNano(0),
                    1L)));

            when(this.issueRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

            // when/then:
            var request = new IssueGetRequest(1000L, 1000L);
            assertThrows(UseCaseException.class, () -> {
                this.usecase.execute(request);
            });
        }

    }

}
