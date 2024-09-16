package jp.wako.demo.springbootmvc.domain.projects;

import java.util.List;
import java.util.Optional;

public interface IProjectRepository {
    List<Project> findAll();
    Long save(final Project project);
    Optional<Project> findById(final Long id);
}
