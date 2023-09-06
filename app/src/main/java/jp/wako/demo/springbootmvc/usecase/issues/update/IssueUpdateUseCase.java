package jp.wako.demo.springbootmvc.usecase.issues.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueUpdateUseCase {

    private final IIssueRepository repository;

    @Transactional
    public IssueUpdateResponse execute(IssueUpdateRequest request) {

        var maybeIssue = this.repository.findById(request.getId());
        var foundIssue = maybeIssue
            .orElseThrow(() -> new UseCaseException("Issue not found."));

        foundIssue.update(request.getTitle(), request.getDescription());
        var updatedIssueId = this.repository.save(foundIssue);

        return new IssueUpdateResponse(updatedIssueId);
    }

}
