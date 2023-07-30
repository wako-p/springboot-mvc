package jp.wako.demo.springbootmvc.usecase.tasks.add;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.domain.tasks.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class AddTaskUseCase {

    private final TaskRepository repository;

    public AddTaskResponse execute(final AddTaskRequest request) {

        var task = Task.create(request.getTitle());
        this.repository.save(task);

        return new AddTaskResponse();
    }

}
