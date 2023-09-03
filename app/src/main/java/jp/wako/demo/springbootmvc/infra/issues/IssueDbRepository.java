package jp.wako.demo.springbootmvc.infra.issues;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntity;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntityDao;
import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import lombok.RequiredArgsConstructor;

@Primary
@RequiredArgsConstructor
@Repository
public class IssueDbRepository implements IssueRepository {

    private final IssueEntityDao dao;

    public List<Issue> findAll() {

        var issueEntites = this.dao.findAll();
        var issues = issueEntites
            .stream()
            .map(this::convertToDomain)
            .collect(Collectors.toList());

        return issues;
    }

    public Optional<Issue> findById(final Integer id) {

        var maybeIssueEntity = this.dao.findById(id);
        var maybeIssue = maybeIssueEntity.map(this::convertToDomain);

        return maybeIssue;
    }

    private Issue convertToDomain(final IssueEntity issueEntity) {
        return Issue.reconstruct(
            issueEntity.getId(),
            issueEntity.getTitle(),
            issueEntity.getDescription(),
            issueEntity.getCreatedAt(),
            issueEntity.getUpdatedAt(),
            issueEntity.getVersion());
    }

    public Integer save(final Issue issue) {

        var issueEntity = convertToEntity(issue);

        if (issue.getId() == null) {

            var result = this.dao.insert(issueEntity);
            var insertedIssueEntity = result.getEntity();

            var insertedIssue = convertToDomain(insertedIssueEntity);
            return insertedIssue.getId();
        }

        try {
            var result = this.dao.update(issueEntity);
            var updatedIssueEntity = result.getEntity();

            var updatedIssue = convertToDomain(updatedIssueEntity);
            return updatedIssue.getId();

        } catch (OptimisticLockingFailureException exception) {
            // NOTE: アプリケーション独自の楽観ロック例外作成してスローした方がよさげ？
            throw new PersistenceException("Update failed. It has already been updated. Please try again.", exception);
        }

    }

    private IssueEntity convertToEntity(final Issue issue) {
        return new IssueEntity(
            issue.getId(),
            issue.getTitle(),
            issue.getDescription(),
            issue.getCreatedAt(),
            issue.getUpdatedAt(),
            issue.getVersion());
    }

    public void delete(final Issue issue) {
        var issueEntity = convertToEntity(issue);
        this.dao.delete(issueEntity);
    }

}
