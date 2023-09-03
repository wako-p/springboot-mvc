package jp.wako.demo.springbootmvc.usecase.issues.delete;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class DeleteIssueUseCase {

    private final IssueRepository repository;

    @Transactional
    public DeleteIssueResponse execute(final DeleteIssueRequest request) {

        var maybeIssue = this.repository.findById(request.getId());
        var foundIssue = maybeIssue
            .orElseThrow(() -> new UseCaseException("Issue not found."));

        this.repository.delete(foundIssue);
        return new DeleteIssueResponse();
    }

}
