package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class InMemoryTaskEntityDao implements ITaskEntityDao {

    private final List<TaskEntity> tasks = new ArrayList<>() {{
        add(new TaskEntity("1", "#3245 Replace InMemory repository with Postgres", "This is a task added for testing purposes.", LocalDateTime.of(2023, 07, 23, 10, 00)));
        add(new TaskEntity("2", "#3246 Change the structure of the VM for a presentation layer task", "This is a task added for testing purposes.", LocalDateTime.of(2023, 07, 23, 10, 30)));
        add(new TaskEntity("3", "#3247 Hide task card description", "This is a task added for testing purposes.", LocalDateTime.of(2023, 07, 23, 11, 00)));
    }};

    public List<TaskEntity> findAll() {
        return this.tasks;
    }

    public Optional<TaskEntity> findBy(final String id) {

        var foundTask = this.tasks
            .stream()
            .filter(addedTask -> addedTask.getId().equals(id))
            .findFirst();

        return foundTask;
    }

    public void save(final TaskEntity task) {

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

    public void delete(final String id) {
        this.tasks.removeIf(addedTask -> addedTask.getId().equals(id));
    }

}