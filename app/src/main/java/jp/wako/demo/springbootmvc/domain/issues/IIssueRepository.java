package jp.wako.demo.springbootmvc.domain.issues;

import java.util.Optional;

public interface IIssueRepository {
    Optional<Issue> findById(final Integer id);
    Integer save(final Issue issue);
    void delete(final Issue issue);
}
