package jp.wako.demo.springbootmvc.usecase.projects.get;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.projects.ProjectRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GetProjectUseCase {

    private final ProjectRepository repository;

    public GetProjectResponse execute(final GetProjectRequest request) {

        var maybeProject = this.repository.findById(request.getId());
        var foundProject = maybeProject
                    .orElseThrow(() -> new UseCaseException("Project not found."));

        return new GetProjectResponse(
            foundProject.getId(),
            foundProject.getName(),
            foundProject.getDescription(),
            foundProject.getIssues());
    }
}
