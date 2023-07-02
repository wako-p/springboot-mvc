package jp.wako.demo.springbootmvc.usecase.task.update;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.task.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class UpdateTaskUseCase {

    private final ITaskRepository repository;

    public UpdateTaskResponse execute(UpdateTaskRequest request) {
        var task = this.repository.findBy(request.getId());

        task.updateComment(request.getComment());
        this.repository.save(task);

        var response = new UpdateTaskResponse(task.getId(), task.getTitle(), task.getComment(), task.isDone());
        return response;
    }
}
