package jp.wako.demo.springbootmvc.usecase.issues.delete;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueDeleteUseCase {

    private final IIssueRepository repository;

    @Transactional
    public IssueDeleteResponse execute(final IssueDeleteRequest request) {

        var maybeIssue = this.repository.findById(request.getId());
        var foundIssue = maybeIssue
            .orElseThrow(() -> new UseCaseException("Issue not found."));

        this.repository.delete(foundIssue);
        return new IssueDeleteResponse();
    }

}
