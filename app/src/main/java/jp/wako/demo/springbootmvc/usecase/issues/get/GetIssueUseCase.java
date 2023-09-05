package jp.wako.demo.springbootmvc.usecase.issues.get;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GetIssueUseCase {

    private final IssueRepository repository;

    public GetIssueResponse execute(final GetIssueRequest request) {

        var maybeIssue = this.repository.findById(request.getId());
        var foundIssue = maybeIssue
            .orElseThrow(() -> new UseCaseException("Issue not found."));

        return new GetIssueResponse(
            foundIssue.getId(),
            foundIssue.getProjectId(),
            foundIssue.getTitle(),
            foundIssue.getDescription(),
            foundIssue.getVersion());
    }
}
