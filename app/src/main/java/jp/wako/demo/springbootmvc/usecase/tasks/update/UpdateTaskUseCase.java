package jp.wako.demo.springbootmvc.usecase.tasks.update;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.tasks.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class UpdateTaskUseCase {

    private final ITaskRepository repository;

    public UpdateTaskResponse execute(UpdateTaskRequest request) {
        var task = this.repository.findBy(request.getId());

        task.updateTitle(request.getTitle());
        task.updateDescription(request.getDescription());
        this.repository.save(task);

        var response = new UpdateTaskResponse(task.getId());
        return response;
    }
}
