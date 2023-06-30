package jp.wako.demo.springbootmvc.usecase.task.add;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.task.ITaskRepository;
import jp.wako.demo.springbootmvc.domain.task.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class AddTaskUseCase {

    private final ITaskRepository repository;

    public AddTaskResponse execute(final AddTaskRequest request) {

        var task = Task.create(request.getTitle());
        this.repository.insert(task);

        return new AddTaskResponse(task.getId(), task.getTitle(), task.isDone());
    }

}
