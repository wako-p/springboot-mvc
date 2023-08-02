package jp.wako.demo.springbootmvc.usecase.tasks.delete;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class DeleteTaskUseCase {

    private final TaskRepository repository;

    @Transactional
    public DeleteTaskResponse execute(final DeleteTaskRequest request) {

        var maybeTask = this.repository.findBy(Integer.parseInt(request.getId()));

        var foundTask = maybeTask
            .orElseThrow(() -> new DomainException(""));

        this.repository.delete(foundTask);
        return new DeleteTaskResponse();
    }

}
