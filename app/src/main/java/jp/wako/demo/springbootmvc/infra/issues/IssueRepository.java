package jp.wako.demo.springbootmvc.infra.issues;

import java.util.Optional;

import org.seasar.doma.jdbc.UniqueConstraintException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.dao.QueryTimeoutException;
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

    @Override
    public Optional<Issue> findById(final Integer id) {

        var maybeIssueEntity = this.dao.selectById(id);
        var maybeIssue = maybeIssueEntity.map(this.converter::toDomain);

        return maybeIssue;
    }

    @Override
    public Integer save(final Issue issue) {

        var issueEntity = this.converter.toEntity(issue);

        try {
            // 新規
            if (issueEntity.getId() == null) {
                var result = this.dao.insert(issueEntity);
                var insertedIssue = this.converter.toDomain(result.getEntity());
                return insertedIssue.getId();
            // 更新
            } else {
                var result = this.dao.update(issueEntity);
                var updatedIssue = this.converter.toDomain(result.getEntity());
                return updatedIssue.getId();
            }
        } catch (OptimisticLockingFailureException ex) {
            throw new PersistenceException("Save failed. It has already been updated. Please try again.", ex);
        } catch (UniqueConstraintException ex) {
            throw new PersistenceException("Save failed. Unique constraint violation.", ex);
        } catch (QueryTimeoutException ex) {
            throw new PersistenceException("Save failed. Query timeout.", ex);
        }
    }

    @Override
    public void delete(final Issue issue) {
        var issueEntity = this.converter.toEntity(issue);
        this.dao.delete(issueEntity);
    }

}
