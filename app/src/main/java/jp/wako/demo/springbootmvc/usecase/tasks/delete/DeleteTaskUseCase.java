package jp.wako.demo.springbootmvc.usecase.tasks.delete;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeleteTaskUseCase {

    private final TaskRepository repository;

    @Transactional
    public DeleteTaskResponse execute(final DeleteTaskRequest request) {

        var maybeTask = this.repository.findBy(Integer.parseInt(request.getId()));
        var foundTask = maybeTask
            .orElseThrow(() -> new UseCaseException(""));

        this.repository.delete(foundTask);
        return new DeleteTaskResponse();
    }

}
