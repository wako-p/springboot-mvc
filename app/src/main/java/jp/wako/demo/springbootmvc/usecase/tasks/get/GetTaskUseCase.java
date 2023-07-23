package jp.wako.demo.springbootmvc.usecase.tasks.get;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.domain.tasks.ITaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class GetTaskUseCase {

    private final ITaskRepository repository;

    public GetTaskResponse execute(GetTaskRequest request) {

        var maybeTask = this.repository.findBy(request.getId());
        var foundTask = maybeTask.orElseThrow(() -> {
            throw new DomainException("");
        });

        var response = new GetTaskResponse(foundTask.getId(), foundTask.getTitle(), foundTask.getDescription());
        return response;
    }
}
