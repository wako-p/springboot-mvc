package jp.wako.demo.springbootmvc.usecase.issues.create;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CreateIssueUseCase {

    private final IssueRepository repository;

    @Transactional
    public CreateIssueResponse execute(final CreateIssueRequest request) {

        var issue = Issue.create(request.getTitle());
        var createdIssueId = this.repository.save(issue);

        return new CreateIssueResponse(createdIssueId);
    }

}
