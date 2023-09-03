package jp.wako.demo.springbootmvc.infra.projects.dao;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import jp.wako.demo.springbootmvc.domain.projects.Project;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntityConverter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public final class ProjectEntityConverter {

    private final IssueEntityConverter converter;

    public Project toDomain(final ProjectEntity projectEntity) {
        return Project.reconstruct(
            projectEntity.getId(),
            projectEntity.getName(),
            projectEntity.getDescription(),
            projectEntity.getIssues()
                .stream()
                .map(this.converter::toDomain)
                .collect(Collectors.toList()),
            projectEntity.getCreatedAt(),
            projectEntity.getUpdatedAt(),
            projectEntity.getVersion());
    }

}
