package jp.wako.demo.springbootmvc.infra.issues.dao;

import org.springframework.stereotype.Component;

import jp.wako.demo.springbootmvc.domain.issues.Issue;

@Component
public final class IssueEntityConverter {

    public Issue toDomain(final IssueEntity issueEntity) {
        return Issue.reconstruct(
            issueEntity.getId(),
            issueEntity.getProjectId(),
            issueEntity.getTitle(),
            issueEntity.getDescription(),
            issueEntity.getCreatedAt(),
            issueEntity.getUpdatedAt(),
            issueEntity.getVersion());
    }

    public IssueEntity toEntity(final Issue issue) {
        return new IssueEntity(
            issue.getId(),
            issue.getProjectId(),
            issue.getTitle(),
            issue.getDescription(),
            issue.getCreatedAt(),
            issue.getUpdatedAt(),
            issue.getVersion());
    }

}
