package jp.wako.demo.springbootmvc.usecase.tasks.delete;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.tasks.ITaskRepository;
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
