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

import jp.wako.demo.springbootmvc.infra.issues.IssueSearchQuery;
import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.IssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectCreateVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectIndexVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectIssuesVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectVM;
import jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel.ProjectViewVM;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchRequest;
import jp.wako.demo.springbootmvc.usecase.projects.create.ProjectCreateRequest;
import jp.wako.demo.springbootmvc.usecase.projects.create.ProjectCreateUseCase;
import jp.wako.demo.springbootmvc.usecase.projects.get.ProjectGetRequest;
import jp.wako.demo.springbootmvc.usecase.projects.get.ProjectGetUseCase;
import jp.wako.demo.springbootmvc.usecase.projects.getall.ProjectGetAllRequest;
import jp.wako.demo.springbootmvc.usecase.projects.getall.ProjectGetAllUseCase;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectGetAllUseCase projectGetAllUseCase;
    private final ProjectCreateUseCase projectCreateUseCase;
    private final ProjectGetUseCase projectGetUseCase;
    private final IssueSearchQuery issueSearchQuery;

    @ModelAttribute("projectIndexVM")
    public ProjectIndexVM createProjectIndexVM() {
        var vm = new ProjectIndexVM();
        return vm;
    }

    @GetMapping({"", "/"})
    public String index(@ModelAttribute("projectIndexVM") final ProjectIndexVM vm) {

        var request = new ProjectGetAllRequest();
        var response = this.projectGetAllUseCase.execute(request);

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
            var request = new ProjectCreateRequest(vm.getName(), vm.getDescription());
            var response = this.projectCreateUseCase.execute(request);

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

        var request = new ProjectGetRequest(id);
        var response = this.projectGetUseCase.execute(request);

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

        // TODO: UseCaseException補足する
        var projectGetRequest = new ProjectGetRequest(id);
        var projectGetResponse = this.projectGetUseCase.execute(projectGetRequest);

        var issueSearchRequest = new IssueSearchRequest(id);
        var issueSearchResponse = this.issueSearchQuery.execute(issueSearchRequest);

        var issues = issueSearchResponse.getIssues()
            .stream()
            .map(issue -> {
                var issueVM = new IssueVM(
                    issue.getId(),
                    issue.getTitle(),
                    issue.getDescription());
                return issueVM;
            })
            .collect(Collectors.toList());

        projectIssuesVM.setId(projectGetResponse.getId());
        projectIssuesVM.setName(projectGetResponse.getName());
        projectIssuesVM.setIssues(issues);

        return "/projects/issues";
    }

}
