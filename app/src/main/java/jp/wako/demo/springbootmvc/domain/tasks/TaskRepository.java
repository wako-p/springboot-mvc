package jp.wako.demo.springbootmvc.domain.tasks;

import java.util.List;
import java.util.Optional;

public interface TaskRepository {
    List<Task> findAll();
    void delete(final int id);
    Optional<Task> findBy(final int id);
    void save(final Task task);
}
