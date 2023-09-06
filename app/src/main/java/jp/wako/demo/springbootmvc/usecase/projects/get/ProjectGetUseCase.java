package jp.wako.demo.springbootmvc.usecase.projects.get;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.projects.IProjectRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectGetUseCase {

    private final IProjectRepository repository;

    public ProjectGetResponse execute(final ProjectGetRequest request) {

        var maybeProject = this.repository.findById(request.getId());
        var foundProject = maybeProject
                    .orElseThrow(() -> new UseCaseException("Project not found."));

        return new ProjectGetResponse(
            foundProject.getId(),
            foundProject.getName(),
            foundProject.getDescription(),
            foundProject.getIssues());
    }
}
