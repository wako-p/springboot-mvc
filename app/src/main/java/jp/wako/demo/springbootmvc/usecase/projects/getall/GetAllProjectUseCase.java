package jp.wako.demo.springbootmvc.usecase.projects.getall;

import org.springframework.stereotype.Service;

import jp.wako.demo.springbootmvc.domain.projects.ProjectRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class GetAllProjectUseCase {

    private final ProjectRepository repository;

    public GetAllProjectResponse execute(final GetAllProjectRequest request) {
        var projects = this.repository.findAll();
        return new GetAllProjectResponse(projects);
    }
}
