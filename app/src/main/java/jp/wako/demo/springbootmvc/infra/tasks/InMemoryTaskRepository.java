package jp.wako.demo.springbootmvc.infra.tasks;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.tasks.ITaskRepository;
import jp.wako.demo.springbootmvc.domain.tasks.Task;
import jp.wako.demo.springbootmvc.infra.tasks.dao.ITaskEntityDao;
import jp.wako.demo.springbootmvc.infra.tasks.dao.InMemoryTaskEntityDao;
import jp.wako.demo.springbootmvc.infra.tasks.dao.TaskEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public final class InMemoryTaskRepository implements ITaskRepository {

    private final ITaskEntityDao dao = new InMemoryTaskEntityDao();

    public List<Task> findAll() {

        var foundTaskEntities = this.dao.findAll();
        var foundTasks = foundTaskEntities
            .stream()
            .map(this::convertEntityToDomain)
            .collect(Collectors.toList());

        return foundTasks;
    }

    public Optional<Task> findBy(final String id) {

        var maybeTaskEntity = this.dao.findBy(id);
        var maybeTask = maybeTaskEntity.map(this::convertEntityToDomain);

        return maybeTask;
    }

    private Task convertEntityToDomain(final TaskEntity taskEntity) {
        return Task.reconstruct(
            taskEntity.getId(),
            taskEntity.getTitle(),
            taskEntity.getDescription(),
            taskEntity.getCreateAt());
    }

    public void save(final Task task) {
        var taskEntity = convertDomainToEntity(task);
        this.dao.save(taskEntity);
    }

    private TaskEntity convertDomainToEntity(final Task task) {
        return new TaskEntity(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCreateAt());
    }

    public void delete(final String id) {
        this.dao.delete(id);
    }

}
