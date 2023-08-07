package jp.wako.demo.springbootmvc.infra.tasks.dao;

import java.util.List;
import java.util.Optional;
import org.seasar.doma.Dao;
import org.seasar.doma.Delete;
import org.seasar.doma.Insert;
import org.seasar.doma.Select;
import org.seasar.doma.Sql;
import org.seasar.doma.Update;
import org.seasar.doma.boot.ConfigAutowireable;
import org.seasar.doma.jdbc.Result;

// @ConfigAutowireableを付けておくと
// Implを自動生成したときに@Repositoryを付加してくれるらしい
// これ外したらBean見つからないって怒られた
@ConfigAutowireable
@Dao
public interface TaskEntityDao {

    @Select
    @Sql("""
            select * from tasks
            """)
    List<TaskEntity> findAll();

    @Select
    @Sql("""
            select * from tasks where id = /* id */0
            """)
    Optional<TaskEntity> findById(final int id);

    @Insert
    Result<TaskEntity> insert(final TaskEntity task);

    @Update
    Result<TaskEntity> update(final TaskEntity task);

    @Delete
    Result<TaskEntity> delete(final TaskEntity task);
}
