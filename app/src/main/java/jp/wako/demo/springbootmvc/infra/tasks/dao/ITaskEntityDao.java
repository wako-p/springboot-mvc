package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.util.List;
import java.util.Optional;

public interface ITaskEntityDao {
    List<TaskEntity> findAll();
    Optional<TaskEntity> findBy(final String id);
    void save(final TaskEntity task);
    void delete(final String id);
}
