package jp.wako.demo.springbootmvc.usecase.tasks.get;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.shared.exception.DomainException;
import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public final class GetTaskUseCase {

    private final TaskRepository repository;

    public GetTaskResponse execute(final GetTaskRequest request) {

        var maybeTask = this.repository.findBy(Integer.parseInt(request.getId()));
        var foundTask = maybeTask.orElseThrow(() -> {
            throw new DomainException("");
        });

        var response = new GetTaskResponse(foundTask.getId().toString(), foundTask.getTitle(), foundTask.getDescription());
        return response;
    }
}
