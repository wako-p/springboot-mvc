package jp.wako.demo.springbootmvc.usecase.issues.get;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueGetUseCase {

    private final IIssueRepository repository;

    public IssueGetResponse execute(final IssueGetRequest request) {

        var maybeIssue = this.repository.findById(request.getId());
        var foundIssue = maybeIssue
            .orElseThrow(() -> new UseCaseException("Issue not found."));

        return new IssueGetResponse(
            foundIssue.getId(),
            foundIssue.getProjectId(),
            foundIssue.getTitle(),
            foundIssue.getDescription(),
            foundIssue.getVersion());
    }
}
