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
        add(Task.reconstruct("1", "Task1", "This is a task added for testing purposes.", false));
        add(Task.reconstruct("2", "Task2", "This is a task added for testing purposes.", false));
        add(Task.reconstruct("3", "Task3", "This is a task added for testing purposes.", false));
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

    public void save(final Task newTask) {
        this.tasks.replaceAll((task) -> {
            if (task.getId().equals(newTask.getId())) {
                return newTask;
            }
            return task;
        });
    }

}
