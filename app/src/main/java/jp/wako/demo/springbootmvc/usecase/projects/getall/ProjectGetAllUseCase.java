package jp.wako.demo.springbootmvc.usecase.projects.getall;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.projects.IProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProjectGetAllUseCase {

    private final IProjectRepository repository;

    public ProjectGetAllResponse execute(final ProjectGetAllRequest request) {
        var projects = this.repository.findAll();
        return new ProjectGetAllResponse(projects);
    }
}
