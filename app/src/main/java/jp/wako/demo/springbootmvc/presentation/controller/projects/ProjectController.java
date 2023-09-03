package jp.wako.demo.springbootmvc.presentation.controller.projects;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectIndexVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectVM;
import jp.wako.demo.springbootmvc.usecase.projects.getall.GetAllProjectRequest;
import jp.wako.demo.springbootmvc.usecase.projects.getall.GetAllProjectUseCase;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final GetAllProjectUseCase getAllProjectUseCase;

    @ModelAttribute("projectIndexVM")
    public ProjectIndexVM createProjectIndexVM() {
        var vm = new ProjectIndexVM();
        return vm;
    }

    @GetMapping({"", "/"})
    public String index(@ModelAttribute("projectIndexVM") final ProjectIndexVM vm) {

        var request = new GetAllProjectRequest();
        var response = this.getAllProjectUseCase.execute(request);

        var projects = response.getProjects()
            .stream()
            .map(project -> {

                var projectVM = new ProjectVM();
                projectVM.setId(project.getId());
                projectVM.setName(project.getName());
                projectVM.setDescription(project.getDescription());
                projectVM.setCreatedAt(project.getCreatedAt());
                projectVM.setUpdatedAt(project.getUpdatedAt());

                return projectVM;
            })
            .collect(Collectors.toList());

        vm.setProjects(projects);

        return "/projects/index";
    }

}
