package jp.wako.demo.springbootmvc.usecase.tasks.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import jp.wako.demo.springbootmvc.usecase.shared.OptimisticLockVersionValidator;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UpdateTaskUseCase {

    private final TaskRepository repository;
    private final OptimisticLockVersionValidator versionValidator;

    @Transactional
    public UpdateTaskResponse execute(UpdateTaskRequest request) {

        var maybeTask = this.repository.findBy(request.getId());
        var foundTask = maybeTask
            .orElseThrow(() -> new UseCaseException("Task(id:" + request.getId() + ") not found."));

        // NOTE: バージョンの比較ロジック(インフラ層の責務？)をユースケースに持ち込むのはいかがなものか？
        if (!this.versionValidator
            .validate(request.getVersion(), foundTask.getVersion())) {
            throw new PersistenceException("Update failed. It has already been updated by another user. Please try again.");
        }

        foundTask.updateTitle(request.getTitle());
        foundTask.updateDescription(request.getDescription());
        var updatedTaskId = this.repository.save(foundTask);

        var response = new UpdateTaskResponse(updatedTaskId);
        return response;
    }

}
