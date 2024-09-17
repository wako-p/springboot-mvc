package jp.wako.demo.springbootmvc.usecase.issues.create;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueCreateUseCase {

    private final IIssueRepository issueRepository;

    @Transactional
    public IssueCreateResponse execute(final IssueCreateRequest request) {

        var issue = Issue.create(
            request.getProjectId(),
            request.getTitle(),
            request.getDescription());

        var createdIssueId = this.issueRepository.save(issue);

        var maybeCreatedIssue = this.issueRepository.findById(createdIssueId);
        var foundCreatedIssue = maybeCreatedIssue
            .orElseThrow(() -> new UseCaseException("Created issue not found."));

        return new IssueCreateResponse(
            foundCreatedIssue.getId(),
            foundCreatedIssue.getProjectId(),
            foundCreatedIssue.getTitle(),
            foundCreatedIssue.getDescription());
    }

}
