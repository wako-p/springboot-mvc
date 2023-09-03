package jp.wako.demo.springbootmvc.usecase.issues.getall;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.issues.IssueRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GetAllIssueUseCase {

    private final IssueRepository repository;

    public GetAllIssueResponse execute(final GetAllIssueRequest request) {
        var issues = this.repository.findAll();
        return new GetAllIssueResponse(issues);
    }
}
