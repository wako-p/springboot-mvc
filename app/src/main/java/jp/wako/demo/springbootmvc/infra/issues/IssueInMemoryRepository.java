package jp.wako.demo.springbootmvc.infra.issues;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import jp.wako.demo.springbootmvc.infra.issues.dao.IssueEntity;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Repository
public class IssueInMemoryRepository implements IssueRepository {

    private final List<IssueEntity> issueEntities = new ArrayList<>() {{
        add(new IssueEntity(1, "#3245 Replace InMemory repository with Postgres", "This is a issue added for testing purposes.", LocalDateTime.of(2023, 07, 23, 10, 00, 00), LocalDateTime.of(2023, 07, 23, 10, 00, 00), 1));
        add(new IssueEntity(2, "#3246 Change the structure of the VM for a presentation layer issue", "This is a issue added for testing purposes.", LocalDateTime.of(2023, 07, 23, 10, 30, 00), LocalDateTime.of(2023, 07, 23, 10, 30, 00), 1));
        add(new IssueEntity(3, "#3247 Hide issue card description", "This is a issue added for testing purposes.", LocalDateTime.of(2023, 07, 23, 11, 00, 00), LocalDateTime.of(2023, 07, 23, 11, 00, 00), 1));
    }};

    public List<Issue> findAll() {

        var issues = issueEntities
            .stream()
            .map(this::convertToDomain)
            .collect(Collectors.toList());

        return issues;
    }

    public Optional<Issue> findById(final Integer id) {

        var foundIssueEntity = this.issueEntities
            .stream()
            .filter(issueEntity -> issueEntity.getId() == id)
            .findFirst();

        var maybeIssue = foundIssueEntity.map(this::convertToDomain);
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

        if (issue.getId() == null) {

            var insertIssueEntity = new IssueEntity(
                generateId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getCreatedAt(),
                issue.getUpdatedAt(),
                issue.getVersion());

            this.issueEntities.add(insertIssueEntity);
            return insertIssueEntity.getId();

        } else {

            var maybeIssueEntity = this.issueEntities
                .stream()
                .filter(issueEntity -> issueEntity.getId() == issue.getId())
                .findFirst();

            maybeIssueEntity.orElseThrow(
                () -> new IllegalArgumentException("Issue with ID " + issue.getId() + " not found.")
            );
            var foundIssueEntity = maybeIssueEntity.get();

            var updateIssueEntity = new IssueEntity(
                generateId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getCreatedAt(),
                issue.getUpdatedAt(),
                issue.getVersion() + 1);

            this.issueEntities.set(issueEntities.indexOf(foundIssueEntity), updateIssueEntity);
            return updateIssueEntity.getId();

        }
    }

    private Integer generateId() {
        return this.issueEntities.size() + 1;
    }

    public void delete(final Issue issue) {
        this.issueEntities.removeIf(
            issueEntity -> issueEntity.getId() == issue.getId());
    }

}
