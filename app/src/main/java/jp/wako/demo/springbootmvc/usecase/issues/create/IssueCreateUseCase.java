package jp.wako.demo.springbootmvc.usecase.issues.create;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueCreateUseCase {

    private final IIssueRepository repository;

    @Transactional
    public IssueCreateResponse execute(final IssueCreateRequest request) {

        var issue = Issue.create(
            request.getProjectId(),
            request.getTitle(),
            request.getDescription());

        var createdIssueId = this.repository.save(issue);

        return new IssueCreateResponse(createdIssueId);
    }

}
