package jp.wako.demo.springbootmvc.presentation.controller.projects;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.IssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectCreateVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectIndexVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectIssuesVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectViewVM;
import jp.wako.demo.springbootmvc.usecase.projects.create.CreateProjectRequest;
import jp.wako.demo.springbootmvc.usecase.projects.create.CreateProjectUseCase;
import jp.wako.demo.springbootmvc.usecase.projects.get.GetProjectRequest;
import jp.wako.demo.springbootmvc.usecase.projects.get.GetProjectUseCase;
import jp.wako.demo.springbootmvc.usecase.projects.getall.GetAllProjectRequest;
import jp.wako.demo.springbootmvc.usecase.projects.getall.GetAllProjectUseCase;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final GetAllProjectUseCase getAllProjectUseCase;
    private final CreateProjectUseCase createProjectUseCase;
    private final GetProjectUseCase getProjectUseCase;

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

    @ModelAttribute("projectCreateVM")
    public ProjectCreateVM createProjectCreateVM() {
        var vm = new ProjectCreateVM();
        return vm;
    }

    @GetMapping("/create")
    public String create(@ModelAttribute("projectCreateVM") final ProjectCreateVM vm) {
        return "/projects/create";
    }

    @PostMapping("/create")
    public String create(
        @ModelAttribute("projectCreateVM") @Validated final ProjectCreateVM vm,
        final BindingResult bindingResult,
        final Model model,
        final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // model.addAttribute("alertMessage", "test");
            return "/projects/create";
        }

        try {
            var request = new CreateProjectRequest(vm.getName(), vm.getDescription());
            var response = this.createProjectUseCase.execute(request);

            return "redirect:/projects/" + response.getId() + "/view";

        } catch (PersistenceException e) {
            model.addAttribute("message", e.getMessage());
            return "/projects/create";
        }
    }

    @ModelAttribute("projectViewVM")
    public ProjectViewVM createProjectViewVM() {
        var vm = new ProjectViewVM();
        return vm;
    }

    @GetMapping("{id}/view")
    public String view(
        @PathVariable Integer id,
        @ModelAttribute("projectViewVM") final ProjectViewVM vm) {

        var request = new GetProjectRequest(id);
        var response = this.getProjectUseCase.execute(request);

        // TODO: プロジェクトが存在しなかったときの処理かく

        vm.setId(response.getId());
        vm.setName(response.getName());
        vm.setDescription(response.getDescription());

        return "projects/view";
    }

    @GetMapping("/{id}/issues")
    public String issues(
        @PathVariable Integer id,
        @ModelAttribute("projectIssuesVM") final ProjectIssuesVM projectIssuesVM) {

        // TODO: ProjectとIssueはID連携にするのでGetIssusUseCaseをDIしてそれ使う
        var request = new GetProjectRequest(id);
        var response = this.getProjectUseCase.execute(request);

        var issues = response.getIssues()
            .stream()
            .map(issue -> {
                var issueVM = new IssueVM(
                    issue.getId(),
                    issue.getTitle(),
                    issue.getDescription());
                return issueVM;
            })
            .collect(Collectors.toList());

        projectIssuesVM.setName(response.getName());
        projectIssuesVM.setIssues(issues);

        return "/projects/issues";
    }

}
