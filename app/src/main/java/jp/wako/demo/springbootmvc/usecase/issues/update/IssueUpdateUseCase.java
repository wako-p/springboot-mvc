package jp.wako.demo.springbootmvc.usecase.issues.update;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import jp.wako.demo.springbootmvc.domain.projects.IProjectRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueUpdateUseCase {

    private final IProjectRepository projectRepository;
    private final IIssueRepository repository;

    @Transactional
    public IssueUpdateResponse execute(IssueUpdateRequest request) {

        var maybeProject = this.projectRepository.findById(request.getProjectId());
        maybeProject
            .orElseThrow(() -> new UseCaseException("Project not found."));

        var maybeIssue = this.repository.findById(request.getId());
        var foundIssue = maybeIssue
            .orElseThrow(() -> new UseCaseException("Issue not found."));

        foundIssue.update(request.getTitle(), request.getDescription());
        var updatedIssueId = this.repository.save(foundIssue);

        var maybeUpdatedIssue = this.repository.findById(updatedIssueId);
        var foundUpdatedIssue = maybeUpdatedIssue
            .orElseThrow(() -> new UseCaseException("Updated issue not found."));

        return new IssueUpdateResponse(
            foundUpdatedIssue.getId(),
            foundUpdatedIssue.getProjectId(),
            foundUpdatedIssue.getTitle(),
            foundUpdatedIssue.getDescription());
    }

}
