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

import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchRequest;

// @ConfigAutowireableを付けておくと
// Implを自動生成したときに@Repositoryを付加してくれるらしい
// これ外したらBean見つからないって怒られた
@ConfigAutowireable
@Dao
public interface IssueEntityDao {

    @Select
    @Sql("""
            select *
            from
                issues
            where
                project_id = /* request.projectId */0
            /*%if request.title != null && !request.title.isEmpty() */
                and title like /* @infix(request.title) */'title'
            /*%end*/
            order by
            /*%if request.sort != null && !request.sort.isEmpty() &&
                    request.order != null && !request.order.isEmpty() */
                /*# " " + request.sort + " " + request.order */
            /*%else */
                /*# "id asc" */
            /*%end */
            """)
    List<IssueEntity> selectBy(final IssueSearchRequest request);

    @Select
    @Sql("""
            select *
            from issues
            where id = /* id */0
            """)
    Optional<IssueEntity> selectById(final Integer id);

    @Select
    @Sql("""
            select *
            from issues
            where project_id = /* projectId */0
            """)
    List<IssueEntity> selectByProjectId(final Integer projectId);

    @Insert
    Result<IssueEntity> insert(final IssueEntity issue);

    @Update
    Result<IssueEntity> update(final IssueEntity issue);

    @Delete
    Result<IssueEntity> delete(final IssueEntity issue);
}
