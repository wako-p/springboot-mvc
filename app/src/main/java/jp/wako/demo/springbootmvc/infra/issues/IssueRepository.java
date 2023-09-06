package jp.wako.demo.springbootmvc.infra.issues;

import java.util.Optional;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntityConverter;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntityDao;
import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import lombok.RequiredArgsConstructor;

@Primary
@RequiredArgsConstructor
@Repository
public class IssueRepository implements IIssueRepository {

    private final IssueEntityDao dao;
    private final IssueEntityConverter converter;

    public Optional<Issue> findById(final Integer id) {

        var maybeIssueEntity = this.dao.selectById(id);
        var maybeIssue = maybeIssueEntity.map(this.converter::toDomain);

        return maybeIssue;
    }

    public Integer save(final Issue issue) {

        var issueEntity = this.converter.toEntity(issue);

        if (issue.getId() == null) {

            var result = this.dao.insert(issueEntity);
            var insertedIssueEntity = result.getEntity();

            var insertedIssue = this.converter.toDomain(insertedIssueEntity);
            return insertedIssue.getId();
        }

        try {
            var result = this.dao.update(issueEntity);
            var updatedIssueEntity = result.getEntity();

            var updatedIssue = this.converter.toDomain(updatedIssueEntity);
            return updatedIssue.getId();

        } catch (OptimisticLockingFailureException exception) {
            // NOTE: アプリケーション独自の楽観ロック例外作成してスローした方がよさげ？
            throw new PersistenceException("Update failed. It has already been updated. Please try again.", exception);
        }

    }

    public void delete(final Issue issue) {
        var issueEntity = this.converter.toEntity(issue);
        this.dao.delete(issueEntity);
    }

}
