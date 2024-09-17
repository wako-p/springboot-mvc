package jp.wako.demo.springbootmvc.usecase.issues.fetch;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueFetchUseCase {

    private final IIssueRepository issueRepository;

    public IssueFetchResponse execute(final IssueFetchRequest request) {

        var maybeIssue = this.issueRepository.findById(request.getId());
        var foundIssue = maybeIssue
            .orElseThrow(() -> new UseCaseException("Issue not found."));

        return new IssueFetchResponse(
            foundIssue.getId(),
            foundIssue.getProjectId(),
            foundIssue.getTitle(),
            foundIssue.getDescription());
    }
}
