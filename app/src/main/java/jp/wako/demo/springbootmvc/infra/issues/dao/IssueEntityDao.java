package jp.wako.demo.springbootmvc.infra.issues.dao;

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
public interface IssueEntityDao {

    @Select
    @Sql("""
            select * from issues
            """)
    List<IssueEntity> findAll();

    @Select
    @Sql("""
            select * from issues where id = /* id */0
            """)
    Optional<IssueEntity> findById(final Integer id);

    @Insert
    Result<IssueEntity> insert(final IssueEntity issue);

    @Update
    Result<IssueEntity> update(final IssueEntity issue);

    @Delete
    Result<IssueEntity> delete(final IssueEntity issue);
}
