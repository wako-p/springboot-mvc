package jp.wako.demo.springbootmvc.usecase.tasks.create;

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

import jp.wako.demo.springbootmvc.domain.tasks.Task;
import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;

public class CreateTaskUseCaseTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class ExecuteTest {

        @Mock
        private TaskRepository repository;

        @InjectMocks
        private CreateTaskUseCase usecase;

        @Captor
        private ArgumentCaptor<Task> taskCaptor;

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるタイトルを使用して、新規作成されたタスクが保存される。")
        public void success1() {
            // when:
            var request = new CreateTaskRequest("new task title");
            this.usecase.execute(request);

            // then:
            // save()に渡されたタスクのアサーション
            verify(this.repository).save(taskCaptor.capture());
            var capturedTask = taskCaptor.getValue();

            // DTOに含まれるタイトルを使用してタスクが新規作成されている
            assertEquals("new task title", capturedTask.getTitle());
        }

    }

}
