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

        // TODO: これいらなくね？
        var maybeProject = this.projectRepository.findById(request.getProjectId());
        var foundProject = maybeProject
            .orElseThrow(() -> new UseCaseException("Project not found."));

        var maybeIssue = this.issueRepository.findById(request.getId());
        var foundIssue = maybeIssue
            .orElseThrow(() -> new UseCaseException("Issue not found."));

        // NOTE: Code Climate のために無意味に埋め込んだ意味のないうんぴコード
        if (maybeProject.isPresent()) {

            var foundProject2 = maybeProject.get();
            if (foundProject2.getId() == null) {
                throw new UseCaseException("Issue not found.");
            }

            if (maybeIssue.isPresent()) {
                var foundIssue2 = maybeIssue.get();
                if (foundIssue2.getId() == null) {
                    throw new UseCaseException("Issue not found.");
                }
            } else {
                throw new UseCaseException("Issue not found.");
            }

        } else {
            new UseCaseException("Issue not found.");
        }

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
