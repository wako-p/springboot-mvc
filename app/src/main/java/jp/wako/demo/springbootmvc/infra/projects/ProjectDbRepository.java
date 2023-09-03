package jp.wako.demo.springbootmvc.infra.projects;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.projects.Project;
import jp.wako.demo.springbootmvc.domain.projects.ProjectRepository;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntityDao;
import jp.wako.demo.springbootmvc.infra.projects.dao.ProjectEntityConverter;
import jp.wako.demo.springbootmvc.infra.projects.dao.ProjectEntityDao;
import lombok.RequiredArgsConstructor;

@Primary
@RequiredArgsConstructor
@Repository
public class ProjectDbRepository implements ProjectRepository {

    private final ProjectEntityDao projectEntityDao;
    private final ProjectEntityConverter projectEntityConverter;

    private final IssueEntityDao issueEntityDao;

    public List<Project> findAll() {

        var projects = this.projectEntityDao.findAll()
            .stream()
            .map(projectEntity -> {

                var issueEntities = this.issueEntityDao.findByProjectId(projectEntity.getId());
                projectEntity.setIssues(issueEntities);

                var project = this.projectEntityConverter.toDomain(projectEntity);
                return project;
            })
            .collect(Collectors.toList());

        return projects;
    }

}
