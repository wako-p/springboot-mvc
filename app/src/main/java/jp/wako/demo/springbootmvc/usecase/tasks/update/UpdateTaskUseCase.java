package jp.wako.demo.springbootmvc.usecase.tasks.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class UpdateTaskUseCase {

    private final TaskRepository repository;

    @Transactional
    public UpdateTaskResponse execute(UpdateTaskRequest request) {
    
        var maybeTask = this.repository.findBy(Integer.parseInt(request.getId()));
        var foundTask = maybeTask.orElseThrow(() -> {
            throw new DomainException("");
        });

        foundTask.updateTitle(request.getTitle());
        foundTask.updateDescription(request.getDescription());
        this.repository.save(foundTask);

        var response = new UpdateTaskResponse(foundTask.getId().toString());
        return response;
    }
}
