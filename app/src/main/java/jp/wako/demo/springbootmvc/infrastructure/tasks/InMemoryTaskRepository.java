package jp.wako.demo.springbootmvc.infrastructure.tasks;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.tasks.ITaskRepository;
import jp.wako.demo.springbootmvc.domain.tasks.Task;

@Repository
public final class InMemoryTaskRepository implements ITaskRepository {

    // TODO: Setにする
    private final List<Task> tasks = new ArrayList<>() {{
        add(Task.reconstruct("1", "#3245 Replace InMemory repository with Postgres", "This is a task added for testing purposes.", false));
        add(Task.reconstruct("2", "#3246 Change the structure of the VM for a presentation layer task", "This is a task added for testing purposes.", false));
        add(Task.reconstruct("3", "#3247 Hide task card description", "This is a task added for testing purposes.", true));
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
