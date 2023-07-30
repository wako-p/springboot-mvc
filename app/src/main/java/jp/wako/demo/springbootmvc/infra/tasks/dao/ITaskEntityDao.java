package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.util.Optional;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Sql;
import org.seasar.doma.boot.ConfigAutowireable;

// @ConfigAutowireableを付けておくと
// Implを自動生成したときに@Repositoryを付加してくれるらしい
// これ外したらBean見つからないって怒られた
@ConfigAutowireable
@Dao
public interface ITaskEntityDao {
    // @Select
    // List<TaskEntity> findAll();

    @Select
    @Sql("""
            select * from tasks where id = /* id */0
            """)
    Optional<TaskEntity> findBy(final int id);

    // @Update
    // Result<TaskEntity> save(final TaskEntity task);

    // @Delete
    // Result<TaskEntity> delete(final TaskEntity task);
}
