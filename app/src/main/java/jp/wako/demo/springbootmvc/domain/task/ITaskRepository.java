package jp.wako.demo.springbootmvc.domain.task;

import java.util.List;

public interface ITaskRepository {
    List<Task> findAll();
    void insert(final Task task);
    void deleteBy(final String id);
}
