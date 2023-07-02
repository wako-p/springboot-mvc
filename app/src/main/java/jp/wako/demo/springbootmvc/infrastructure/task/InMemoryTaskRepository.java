package jp.wako.demo.springbootmvc.infrastructure.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.task.ITaskRepository;
import jp.wako.demo.springbootmvc.domain.task.Task;

@Repository
public final class InMemoryTaskRepository implements ITaskRepository {

    // TODO: Setにする
    private final List<Task> tasks = new ArrayList<>() {{
        add(Task.create("test1"));
        add(Task.create("test2"));
        add(Task.create("test3"));
    }};

    public List<Task> findAll() {
        return this.tasks;
    }

    public void insert(final Task task) {
        this.tasks.add(task);
    }

    public void deleteBy(final String id) {
        this.tasks.removeIf(task -> task.getId().equals(id));
    }

    public Task findBy(final String id) {

        var foundTask = this.tasks
            .stream()
            .filter(task -> task.getId().equals(id))
            .findFirst();

        return foundTask.get();
    }

}
