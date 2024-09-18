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

import jp.wako.demo.springbootmvc.infra.issues.IssueSearchParameter;

// @ConfigAutowireableを付けておくと
// Implを自動生成したときに@Repositoryを付加してくれるらしい
// これ外したらBean見つからないって怒られた
@ConfigAutowireable
@Dao
public interface IssueEntityDao {

    @Select
    @Sql("""
            select
                *
            from
                issues
            where
                project_id = /* parameter.projectId */1000
            /*%if parameter.title != null && !parameter.title.isEmpty() */
                and title like /* @infix(parameter.title) */'title'
            /*%end*/
            order by
            /*%if parameter.sort != null */
                /*# " " + parameter.sort.toString() */
            /*%else */
                /*# "id asc" */
            /*%end */
        """)
    List<IssueEntity> selectBy(final IssueSearchParameter parameter);

    @Select
    @Sql("""
            select
                *
            from
                issues
            where
                id = /* id */1000
            """)
    Optional<IssueEntity> selectById(final Long id);

    @Select
    @Sql("""
            select
                *
            from
                issues
            where
                project_id = /* projectId */1000
            """)
    List<IssueEntity> selectByProjectId(final Long projectId);

    @Insert
    Result<IssueEntity> insert(final IssueEntity issue);

    @Update
    Result<IssueEntity> update(final IssueEntity issue);

    @Delete
    Result<IssueEntity> delete(final IssueEntity issue);
}
