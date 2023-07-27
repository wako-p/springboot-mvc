package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.util.Optional;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;

@Dao
public interface ITaskEntityDao {
    // @Select
    // List<TaskEntity> findAll();

    @Select
    Optional<TaskEntity> findBy(final int id);

    // @Update
    // Result<TaskEntity> save(final TaskEntity task);

    // @Delete
    // Result<TaskEntity> delete(final TaskEntity task);
}
