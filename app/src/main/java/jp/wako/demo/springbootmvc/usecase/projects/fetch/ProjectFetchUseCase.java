package jp.wako.demo.springbootmvc.usecase.projects.fetch;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.projects.IProjectRepository;
import jp.wako.demo.springbootmvc.usecase.shared.exception.UseCaseException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectFetchUseCase {

    private final IProjectRepository repository;

    public ProjectFetchResponse execute(final ProjectFetchRequest request) {

        var maybeProject = this.repository.findById(request.getId());
        var foundProject = maybeProject
                .orElseThrow(() -> new UseCaseException("Project not found."));

        var foundProjectDto = new ProjectDto(
            foundProject.getId(),
            foundProject.getName(),
            foundProject.getDescription());

        return new ProjectFetchResponse(foundProjectDto);
    }
}
