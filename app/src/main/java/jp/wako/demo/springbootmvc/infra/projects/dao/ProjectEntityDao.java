package jp.wako.demo.springbootmvc.infra.projects.dao;

import java.util.List;
import java.util.Optional;

import org.seasar.doma.Dao;
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
public interface ProjectEntityDao {

    @Select
    @Sql("""
            select *
            from projects
            """)
    List<ProjectEntity> selectAll();

    @Select
    @Sql("""
            select *
            from projects
            where id = /* id */0
            """)
    Optional<ProjectEntity> selectById(final Integer id);

    @Insert
    Result<ProjectEntity> insert(final ProjectEntity project);

    @Update
    Result<ProjectEntity> update(final ProjectEntity project);

}
