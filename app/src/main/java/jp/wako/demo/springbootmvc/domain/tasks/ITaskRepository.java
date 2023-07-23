package jp.wako.demo.springbootmvc.domain.tasks;

import java.util.List;
import java.util.Optional;

public interface ITaskRepository {
    List<Task> findAll();
    void delete(final String id);
    Optional<Task> findBy(final String id);
    void save(final Task task);
}
