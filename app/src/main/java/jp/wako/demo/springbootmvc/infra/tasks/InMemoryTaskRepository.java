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
            .map(founTaskEntity -> Task.reconstruct(
                founTaskEntity.getId(),
                founTaskEntity.getTitle(),
                founTaskEntity.getDescription(),
                founTaskEntity.getCreateAt()))
            .collect(Collectors.toList());

        return foundTasks;
    }

    public Optional<Task> findBy(final String id) {

        var maybeTaskEntity = this.dao.findBy(id);
        var maybeTask = maybeTaskEntity.map(foundTaskEntity -> Task.reconstruct(
                foundTaskEntity.getId(),
                foundTaskEntity.getTitle(),
                foundTaskEntity.getDescription(),
                foundTaskEntity.getCreateAt()));

        return maybeTask;
    }

    public void save(final Task task) {

        var taskEntity = new TaskEntity(
            task.getId(),
            task.getTitle(),
            task.getDescription(),
            task.getCreateAt());

        this.dao.save(taskEntity);
    }

    public void delete(final String id) {
        this.dao.delete(id);
    }

}
