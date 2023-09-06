package jp.wako.demo.springbootmvc.domain.issues;

import java.util.List;
import java.util.Optional;

public interface IIssueRepository {
    List<Issue> findAll();
    Optional<Issue> findById(final Integer id);
    Integer save(final Issue issue);
    void delete(final Issue issue);
}
