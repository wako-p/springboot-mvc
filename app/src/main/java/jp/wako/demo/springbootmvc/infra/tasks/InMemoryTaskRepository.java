package jp.wako.demo.springbootmvc.infra.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.infra.tasks.dao.TaskEntity;
import jp.wako.demo.springbootmvc.domain.tasks.Task;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class InMemoryTaskRepository implements TaskRepository {

    private final List<TaskEntity> taskEntites = new ArrayList<>() {{
        add(new TaskEntity(1, "#3245 Replace InMemory repository with Postgres", "This is a task added for testing purposes.", LocalDateTime.of(2023, 07, 23, 10, 00)));
        add(new TaskEntity(2, "#3246 Change the structure of the VM for a presentation layer task", "This is a task added for testing purposes.", LocalDateTime.of(2023, 07, 23, 10, 30)));
        add(new TaskEntity(3, "#3247 Hide task card description", "This is a task added for testing purposes.", LocalDateTime.of(2023, 07, 23, 11, 00)));
    }};

    public List<Task> findAll() {

        var tasks = taskEntites
            .stream()
            .map(this::convertToDomain)
            .collect(Collectors.toList());

        return tasks;
    }

    public Optional<Task> findBy(final int id) {

        var foundTaskEntity = this.taskEntites
            .stream()
            .filter(taskEntity -> taskEntity.getId().equals(id))
            .findFirst();

        var maybeTask = foundTaskEntity.map(this::convertToDomain);
        return maybeTask;
    }

    private Task convertToDomain(final TaskEntity taskEntity) {
        return Task.reconstruct(
            taskEntity.getId(),
            taskEntity.getTitle(),
            taskEntity.getDescription(),
            taskEntity.getCreateAt());
    }

    public void save(final Task task) {

        if (task.getId() == null) {

            var insertTaskEntity = convertToEntity(task);
            insertTaskEntity.setId(generateId());

            this.taskEntites.add(insertTaskEntity);

        } else {
            var maybeTaskEntity = this.taskEntites
                .stream()
                .filter(taskEntity -> taskEntity.getId() == task.getId())
                .findFirst();

            maybeTaskEntity.orElseThrow(
                () -> new IllegalArgumentException("Task with ID " + task.getId() + " not found.")
            );
            var foundTaskEntity = maybeTaskEntity.get();

            var updateTaskEntity = convertToEntity(task);
            this.taskEntites.set(taskEntites.indexOf(foundTaskEntity), updateTaskEntity);
        }
    }

    private TaskEntity convertToEntity(final Task task) {
        return new TaskEntity(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCreateAt());
    }

    private int generateId() {
        return this.taskEntites.size() + 1;
    }

    public void delete(final int id) {
        this.taskEntites.removeIf(taskEntity -> taskEntity.getId() == id);
    }

}
