package jp.wako.demo.springbootmvc.usecase.task.delete;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.task.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class DeleteTaskUseCase {

    private final ITaskRepository repository;

    public DeleteTaskResponse execute(final DeleteTaskRequest request) {
        this.repository.deleteBy(request.getId());
        return new DeleteTaskResponse();
    }

}
