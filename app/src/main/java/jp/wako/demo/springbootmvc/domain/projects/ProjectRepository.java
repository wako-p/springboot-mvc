package jp.wako.demo.springbootmvc.domain.projects;

import java.util.List;

public interface ProjectRepository {
    List<Project> findAll();
}
