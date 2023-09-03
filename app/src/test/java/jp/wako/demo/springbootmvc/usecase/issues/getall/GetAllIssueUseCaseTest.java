package jp.wako.demo.springbootmvc.usecase.issues.getall;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import jp.wako.demo.springbootmvc.usecase.issues.TestIssueFactory;

public class GetAllIssueUseCaseTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class ExecuteTest {

        @Mock
        private IssueRepository repository;

        @InjectMocks
        private GetAllIssueUseCase usecase;

        @Test
        @DisplayName("DTOを渡すと、全ての課題が取得され、DTOに詰め替えて返される。")
        public void success1() {
            // given:
            when(this.repository.findAll()).thenReturn(new ArrayList<Issue>() {{
                add(TestIssueFactory.create(1000));
                add(TestIssueFactory.create(1001));
                add(TestIssueFactory.create(1002));
            }});

            // when:
            var request = new GetAllIssueRequest();
            var actual = this.usecase.execute(request);

            // then:
            var expected = new GetAllIssueResponse(new ArrayList<Issue>() {{
                add(TestIssueFactory.create(1000));
                add(TestIssueFactory.create(1001));
                add(TestIssueFactory.create(1002));
            }});
            assertEquals(expected, actual);
        }

    }

}
