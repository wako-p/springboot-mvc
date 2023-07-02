package jp.wako.demo.springbootmvc.usecase.task.get;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.task.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class GetTaskUseCase {

    private final ITaskRepository repository;

    public GetTaskResponse execute(GetTaskRequest request) {
        var task = this.repository.findBy(request.getId());
        var response = new GetTaskResponse(task.getId(), task.getTitle(), task.getComment(), task.isDone());
        return response;
    }
}
