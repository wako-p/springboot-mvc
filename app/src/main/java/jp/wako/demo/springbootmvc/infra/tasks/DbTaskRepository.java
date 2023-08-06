package jp.wako.demo.springbootmvc.infra.tasks;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.tasks.TaskRepository;
import jp.wako.demo.springbootmvc.domain.tasks.Task;
import jp.wako.demo.springbootmvc.infra.tasks.dao.TaskEntityDao;
import jp.wako.demo.springbootmvc.infra.tasks.dao.TaskEntity;
import lombok.RequiredArgsConstructor;

@Primary
@RequiredArgsConstructor
@Repository
public class DbTaskRepository implements TaskRepository {

    private final TaskEntityDao dao;

    public List<Task> findAll() {

        var taskEntites = this.dao.findAll();
        var tasks = taskEntites
            .stream()
            .map(this::convertToDomain)
            .collect(Collectors.toList());

        return tasks;
    }

    public Optional<Task> findBy(final int id) {

        var maybeTaskEntity = this.dao.findBy(id);
        var maybeTask = maybeTaskEntity.map(this::convertToDomain);

        return maybeTask;
    }

    private Task convertToDomain(final TaskEntity taskEntity) {
        return Task.reconstruct(
            taskEntity.getId(),
            taskEntity.getTitle(),
            taskEntity.getDescription(),
            taskEntity.getCreatedAt(),
            taskEntity.getVersion());
    }

    public int save(final Task task) {

        var taskEntity = convertToEntity(task);

        if (task.getId() == null) {

            var result = this.dao.insert(taskEntity);
            var insertedTaskEntity = result.getEntity();

            var insertedTask = convertToDomain(insertedTaskEntity);
            return insertedTask.getId();

        } else {

            // TODO: 楽観ロックに失敗したら PersistenceException に変換してスローする
            var result = this.dao.update(taskEntity);
            var updateTaskEntity = result.getEntity();

            var updateTask = convertToDomain(updateTaskEntity);
            return updateTask.getId();

        }
    }

    private TaskEntity convertToEntity(final Task task) {
        return new TaskEntity(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCreateAt(),
            task.getVersion());
    }

    public void delete(final Task task) {
        var taskEntity = convertToEntity(task);
        this.dao.delete(taskEntity);
    }

}
