package jp.wako.demo.springbootmvc.infra.issues;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Primary;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntityDao;
import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import lombok.RequiredArgsConstructor;

@Primary
@RequiredArgsConstructor
@Repository
public class IssueDbRepository implements IssueRepository {

    private final IssueEntityDao dao;
    private final IssueConverter converter;

    public List<Issue> findAll() {

        var issueEntities = this.dao.findAll();
        var issues = issueEntities
            .stream()
            .map(this.converter::toDomain)
            .collect(Collectors.toList());

        return issues;
    }

    public Optional<Issue> findById(final Integer id) {

        var maybeIssueEntity = this.dao.findById(id);
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
