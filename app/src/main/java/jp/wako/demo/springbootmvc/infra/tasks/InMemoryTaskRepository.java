package jp.wako.demo.springbootmvc.infra.tasks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.tasks.ITaskRepository;
import jp.wako.demo.springbootmvc.domain.tasks.Task;
import jp.wako.demo.springbootmvc.infra.tasks.dao.ITaskEntityDao;
import jp.wako.demo.springbootmvc.infra.tasks.dao.TaskEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class InMemoryTaskRepository implements ITaskRepository {

    private final ITaskEntityDao dao;

    // TODO: Setにする
    private final List<Task> tasks = new ArrayList<>() {{
        add(Task.reconstruct("1", "#3245 Replace InMemory repository with Postgres", "This is a task added for testing purposes.", LocalDateTime.of(2023, 07, 23, 10, 00)));
        add(Task.reconstruct("2", "#3246 Change the structure of the VM for a presentation layer task", "This is a task added for testing purposes.", LocalDateTime.of(2023, 07, 23, 10, 30)));
        add(Task.reconstruct("3", "#3247 Hide task card description", "This is a task added for testing purposes.", LocalDateTime.of(2023, 07, 23, 11, 00)));
    }};

    public List<Task> findAll() {
        return this.tasks;
    }

    public void delete(final String id) {
        this.tasks.removeIf(addedTask -> addedTask.getId().equals(id));
    }

    public Optional<Task> findBy(final String id) {

        // NOTE: DAO使えるかのお試し
        var maybeTaskEntity = this.dao.findBy(Integer.parseInt(id));
        var maybeTask = maybeTaskEntity.map(this::convertEntityToDomain);
        return maybeTask;

        // var foundTask = this.tasks
        //     .stream()
        //     .filter(addedTask -> addedTask.getId().equals(id))
        //     .findFirst();

        // return foundTask;
    }

    private Task convertEntityToDomain(final TaskEntity taskEntity) {
        return Task.reconstruct(
            String.valueOf(taskEntity.getId()),
            taskEntity.getTitle(),
            taskEntity.getDescription(),
            taskEntity.getCreateAt());
    }

    public void save(final Task task) {

        // 保存しようとしているタスクが存在しているかチェック
        var exists = this.tasks
            .stream()
            .anyMatch(addedTask -> addedTask.getId().equals(task.getId()));

        // 存在しない場合はそのまま追加
        if (!exists) {
            this.tasks.add(task);
            return;
        }

        // 存在する場合は置き換える
        this.tasks.replaceAll((addedTask) -> {
            if (addedTask.getId().equals(task.getId())) {
                return task;
            }
            return addedTask;
        });

    }

}
