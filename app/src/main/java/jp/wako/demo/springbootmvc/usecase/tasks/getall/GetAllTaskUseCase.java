package jp.wako.demo.springbootmvc.usecase.tasks.getall;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.tasks.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class GetAllTaskUseCase {

    private final ITaskRepository repository;

    public GetAllTaskResponse execute(final GetAllTaskRequest request) {
        var tasks = this.repository.findAll();
        return new GetAllTaskResponse(tasks);
    }
}
