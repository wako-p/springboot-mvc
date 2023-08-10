package jp.wako.demo.springbootmvc.usecase.tasks.delete;

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

public class DeleteTaskUseCaseTest {

    @Nested
    @ExtendWith(MockitoExtension.class)
    public class ExecuteTest {

        @Mock
        private TaskRepository repository;

        @InjectMocks
        private DeleteTaskUseCase usecase;

        @Captor
        private ArgumentCaptor<Task> taskCaptor;

        @Captor
        private ArgumentCaptor<Integer> taskIdCaptor;

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるIDのタスクが取得されている。")
        public void success1() {
            // given:
            when(this.repository.findById(anyInt()))
                .thenReturn(Optional.of(TestTaskFactory.create(100)));

            // when:
            var request = new DeleteTaskRequest(100);
            this.usecase.execute(request);

            // then:
            // 渡されたDTOに含まれるIDのタスクを取得している
            verify(this.repository).findById(taskIdCaptor.capture());
            var capturedTaskId = taskIdCaptor.getValue();
            assertEquals(100, capturedTaskId);
        }

        @Test
        @DisplayName("DTOを渡すと、そのDTOに含まれるIDのタスクが削除される。")
        public void success2() {
            // given:
            when(this.repository.findById(anyInt()))
                .thenReturn(Optional.of(TestTaskFactory.create(100)));

            // when:
            var request = new DeleteTaskRequest(100);
            this.usecase.execute(request);

            // then:
            // delete()に渡されたタスクのアサーション
            verify(this.repository).delete(taskCaptor.capture());
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
            var request = new DeleteTaskRequest(100);
            assertThrows(UseCaseException.class, () -> {
                this.usecase.execute(request);
            });
        }

    }

}
