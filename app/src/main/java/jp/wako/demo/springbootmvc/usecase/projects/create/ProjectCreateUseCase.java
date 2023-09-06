package jp.wako.demo.springbootmvc.usecase.projects.create;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.wako.demo.springbootmvc.domain.projects.Project;
import jp.wako.demo.springbootmvc.domain.projects.IProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectCreateUseCase {

    private final IProjectRepository repository;

    @Transactional
    public ProjectCreateResponse execute(final ProjectCreateRequest request) {

        var project = Project.create(request.getName(), request.getDescription());
        var createdProjectId = this.repository.save(project);

        return new ProjectCreateResponse(createdProjectId);
    }

}
