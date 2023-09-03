package jp.wako.demo.springbootmvc.infra.issues;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntity;

public final class IssueConverter {

    public Issue toDomain(final IssueEntity issueEntity) {
        return Issue.reconstruct(
            issueEntity.getId(),
            issueEntity.getTitle(),
            issueEntity.getDescription(),
            issueEntity.getCreatedAt(),
            issueEntity.getUpdatedAt(),
            issueEntity.getVersion());
    }

    public IssueEntity toEntity(final Issue issue) {
        return new IssueEntity(
            issue.getId(),
            issue.getTitle(),
            issue.getDescription(),
            issue.getCreatedAt(),
            issue.getUpdatedAt(),
            issue.getVersion());
    }

}
