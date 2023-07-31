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
            select /*%expand*/* from tasks
            """)
    List<TaskEntity> findAll();

    @Select
    @Sql("""
            select * from tasks where id = /* id */0
            """)
    Optional<TaskEntity> findBy(final int id);

    @Insert
    @Sql("""
            insert into tasks(title, description)
            values(/* task.title */'', /* task.description */'')
            """)
    Result<TaskEntity> insert(final TaskEntity task);

    @Update
    @Sql("""
            update tasks
            set title = /* task.title */'', description = /* task.description */''
            where id = /* task.id */0
            """)
    Result<TaskEntity> update(final TaskEntity task);

    @Delete
    @Sql("""
            delete from tasks where id = /* id */0
            """)
    int delete(final int id);
}
