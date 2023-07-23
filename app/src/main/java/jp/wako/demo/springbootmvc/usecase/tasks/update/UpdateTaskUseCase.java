package jp.wako.demo.springbootmvc.usecase.tasks.update;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.domain.tasks.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class UpdateTaskUseCase {

    private final ITaskRepository repository;

    public UpdateTaskResponse execute(UpdateTaskRequest request) {
    
        var maybeTask = this.repository.findBy(request.getId());
        var foundTask = maybeTask.orElseThrow(() -> {
            throw new DomainException("");
        });

        foundTask.updateTitle(request.getTitle());
        foundTask.updateDescription(request.getDescription());
        this.repository.save(foundTask);

        var response = new UpdateTaskResponse(foundTask.getId());
        return response;
    }
}
