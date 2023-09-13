package jp.wako.demo.springbootmvc.infra.projects.dao;

import org.springframework.stereotype.Component;

import jp.wako.demo.springbootmvc.domain.projects.Project;

@Component
public final class ProjectEntityConverter {

    public Project toDomain(final ProjectEntity projectEntity) {
        return Project.reconstruct(
            projectEntity.getId(),
            projectEntity.getName(),
            projectEntity.getDescription(),
            projectEntity.getCreatedAt(),
            projectEntity.getUpdatedAt(),
            projectEntity.getVersion());
    }

    public ProjectEntity toEntity(final Project project) {
        return new ProjectEntity(
            project.getId(),
            project.getName(),
            project.getDescription(),
            project.getCreatedAt(),
            project.getUpdatedAt(),
            project.getVersion());
    }
}
