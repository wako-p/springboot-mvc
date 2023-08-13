package jp.wako.demo.springbootmvc.usecase.tasks.getall;

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

import jp.wako.demo.springbootmvc.domain.tasks.Task;
import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.usecase.tasks.TestTaskFactory;

public class GetAllTaskUseCaseTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class ExecuteTest {

        @Mock
        private TaskRepository repository;

        @InjectMocks
        private GetAllTaskUseCase usecase;

        @Test
        @DisplayName("DTOを渡すと、全てのタスクが取得され、DTOに詰め替えて返される。")
        public void success1() {
            // given:
            when(this.repository.findAll()).thenReturn(new ArrayList<Task>() {{
                add(TestTaskFactory.create(100));
                add(TestTaskFactory.create(101));
                add(TestTaskFactory.create(102));
            }});

            // when:
            var request = new GetAllTaskRequest();
            var actual = this.usecase.execute(request);

            // then:
            var expected = new GetAllTaskResponse(new ArrayList<Task>() {{
                add(TestTaskFactory.create(100));
                add(TestTaskFactory.create(101));
                add(TestTaskFactory.create(102));
            }});
            assertEquals(expected, actual);
        }

    }

}
