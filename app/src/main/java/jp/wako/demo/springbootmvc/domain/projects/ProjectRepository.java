package jp.wako.demo.springbootmvc.domain.projects;

import java.util.List;
import java.util.Optional;

public interface ProjectRepository {
    List<Project> findAll();
    Integer save(final Project project);
    Optional<Project> findById(final Integer id);
}
