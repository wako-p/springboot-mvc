package jp.wako.demo.springbootmvc.usecase.tasks.getall;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GetAllTaskUseCase {

    private final TaskRepository repository;

    public GetAllTaskResponse execute(final GetAllTaskRequest request) {
        var tasks = this.repository.findAll();
        return new GetAllTaskResponse(tasks);
    }
}
