package jp.wako.demo.springbootmvc.usecase.issues.get;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.issues.IIssueRepository;
import jp.wako.demo.springbootmvc.domain.projects.IProjectRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class IssueGetUseCase {

    private final IProjectRepository projectRepository;
    private final IIssueRepository issueRepository;

    public IssueGetResponse execute(final IssueGetRequest request) {

        var maybeProject = this.projectRepository.findById(request.getProjectId());
        var foundProject = maybeProject
            .orElseThrow(() -> new UseCaseException("Project not found."));

        var maybeIssue = this.issueRepository.findById(request.getId());
        var foundIssue = maybeIssue
            .orElseThrow(() -> new UseCaseException("Issue not found."));

        var foundProjectDto = new ProjectDto(
            foundProject.getId(),
            foundProject.getName());

        var foundIssueDto = new IssueDto(
            foundIssue.getId(),
            foundIssue.getTitle(),
            foundIssue.getDescription());

        return new IssueGetResponse(foundProjectDto, foundIssueDto);
    }
}
