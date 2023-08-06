package jp.wako.demo.springbootmvc.usecase.tasks.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UpdateTaskUseCase {

    private final TaskRepository repository;

    @Transactional
    public UpdateTaskResponse execute(UpdateTaskRequest request) {

        var maybeTask = this.repository.findBy(request.getId());
        var foundTask = maybeTask
            .orElseThrow(() -> new UseCaseException("Task(id:" + request.getId() + ") not found."));

        foundTask.updateTitle(request.getTitle());
        foundTask.updateDescription(request.getDescription());
        var updatedTaskId = this.repository.save(foundTask);

        var response = new UpdateTaskResponse(updatedTaskId);
        return response;
    }
}
