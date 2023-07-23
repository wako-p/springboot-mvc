package jp.wako.demo.springbootmvc.domain.tasks;

import java.util.List;

public interface ITaskRepository {
    List<Task> findAll();
    void delete(final String id);
    Task findBy(final String id);
    void save(final Task task);
}
