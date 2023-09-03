package jp.wako.demo.springbootmvc.infra.projects.dao;

import java.util.List;
import org.seasar.doma.Dao;
import org.seasar.doma.Select;
import org.seasar.doma.Sql;
import org.seasar.doma.boot.ConfigAutowireable;

// @ConfigAutowireableを付けておくと
// Implを自動生成したときに@Repositoryを付加してくれるらしい
// これ外したらBean見つからないって怒られた
@ConfigAutowireable
@Dao
public interface ProjectEntityDao {

    @Select
    @Sql("""
            select * from projects
            """)
    List<ProjectEntity> findAll();

}
