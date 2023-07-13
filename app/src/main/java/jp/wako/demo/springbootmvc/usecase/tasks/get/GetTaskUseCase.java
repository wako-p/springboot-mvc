package jp.wako.demo.springbootmvc.usecase.tasks.get;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.tasks.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class GetTaskUseCase {

    private final ITaskRepository repository;

    public GetTaskResponse execute(GetTaskRequest request) {
        var task = this.repository.findBy(request.getId());
        var response = new GetTaskResponse(task.getId(), task.getTitle(), task.getDescription(), task.isDone());
        return response;
    }
}
