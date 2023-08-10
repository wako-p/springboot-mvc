package jp.wako.demo.springbootmvc.usecase.tasks.update;

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

import jp.wako.demo.springbootmvc.domain.tasks.Task;
import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import jp.wako.demo.springbootmvc.usecase.tasks.TestTaskFactory;

public class UpdateTaskUseCaseTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class ExecuteTest {

        @Mock
        private TaskRepository repository;

        @InjectMocks
        private UpdateTaskUseCase usecase;

        @Captor
        private ArgumentCaptor<Integer> taskIdCaptor;

        @Captor
        private ArgumentCaptor<Task> taskCaptor;

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるIDのタスクが取得されている。")
        public void success1() {
            // given:
            when(this.repository.findById(anyInt()))
                .thenReturn(Optional.of(TestTaskFactory.create(100)));

            // when:
            var request = new UpdateTaskRequest(100, "title", "description", 1);
            this.usecase.execute(request);

            // then:
            // 渡されたDTOに含まれるIDのタスクを取得している
            verify(this.repository).findById(taskIdCaptor.capture());
            var capturedTaskId = taskIdCaptor.getValue();
            assertEquals(100, capturedTaskId);
        }

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるタイトルと説明で更新されたタスクが保存される。")
        public void success2() {
            // given:
            when(this.repository.findById(anyInt()))
                .thenReturn(Optional.of(TestTaskFactory.create(100)));

            // when:
            var request = new UpdateTaskRequest(100, "title", "description", 1);
            this.usecase.execute(request);

            // then:
            // save()に渡されたタスクのアサーション
            verify(this.repository).save(taskCaptor.capture());
            var capturedTask = taskCaptor.getValue();

            assertEquals(100, capturedTask.getId());
            assertEquals("title", capturedTask.getTitle());
            assertEquals("description", capturedTask.getDescription());
        }

        @Test
        @DisplayName("DTOに含まれるIDのタスクが存在しない場合は例外がスローされる。")
        public void failure1() {
            // given:
            when(this.repository.findById(anyInt()))
                .thenReturn(Optional.empty());

            // when/then:
            var request = new UpdateTaskRequest(100, "title", "description", 1);
            assertThrows(UseCaseException.class, () -> {
                this.usecase.execute(request);
            });
        }

    }
}
