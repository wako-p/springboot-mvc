package jp.wako.demo.springbootmvc.usecase.tasks.create;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.domain.tasks.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CreateTaskUseCase {

    private final TaskRepository repository;

    @Transactional
    public CreateTaskResponse execute(final CreateTaskRequest request) {

        var task = Task.create(request.getTitle());
        this.repository.save(task);

        return new CreateTaskResponse();
    }

}
