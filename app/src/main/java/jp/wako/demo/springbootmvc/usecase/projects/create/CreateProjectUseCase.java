package jp.wako.demo.springbootmvc.usecase.projects.create;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.projects.Project;
import jp.wako.demo.springbootmvc.domain.projects.ProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CreateProjectUseCase {

    private final ProjectRepository repository;

    @Transactional
    public CreateProjectResponse execute(final CreateProjectRequest request) {

        var project = Project.create(request.getName(), request.getDescription());
        var createdProjectId = this.repository.save(project);

        return new CreateProjectResponse(createdProjectId);
    }

}
