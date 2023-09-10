package jp.wako.demo.springbootmvc.presentation.controller.issues;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueCreateVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueEditVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueIndexVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueViewVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.ProjectVM;
import jp.wako.demo.springbootmvc.usecase.issues.create.IssueCreateRequest;
import jp.wako.demo.springbootmvc.usecase.issues.create.IssueCreateUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.delete.IssueDeleteRequest;
import jp.wako.demo.springbootmvc.usecase.issues.delete.IssueDeleteUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.get.IssueGetRequest;
import jp.wako.demo.springbootmvc.usecase.issues.get.IssueGetUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.search.IIssueSearchQuery;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchRequest;
import jp.wako.demo.springbootmvc.usecase.issues.update.IssueUpdateRequest;
import jp.wako.demo.springbootmvc.usecase.issues.update.IssueUpdateUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}")
public class IssueController {

    private final IIssueSearchQuery issueSearchQuery;
    private final IssueGetUseCase issueGetUseCase;
    private final IssueCreateUseCase issueCreateUseCase;
    private final IssueUpdateUseCase issueUpdateUseCase;
    private final IssueDeleteUseCase issueDeleteUseCase;

    @ModelAttribute("issueIndexVM")
    public IssueIndexVM createIssueIndexVM() {
        return new IssueIndexVM();
    }

    @GetMapping("/issues")
    public String index(
        @PathVariable final Integer projectId,
        @ModelAttribute("issueIndexVM") final IssueIndexVM vm) {

        // TODO: UseCaseException補足する
        var request = new IssueSearchRequest(projectId);
        var response = this.issueSearchQuery.execute(request);

        var project = ProjectVM.createFrom(response.getProject());
        var issues = response.getIssues()
            .stream()
            .map(IssueVM::createFrom)
            .collect(Collectors.toList());

        vm.setProject(project);
        vm.setIssues(issues);

        return "/issues/index";
    }

    @ModelAttribute("issueCreateVM")
    private IssueCreateVM createIssueCreateVM() {
        return new IssueCreateVM();
    }

    @GetMapping("/issues/create")
    public String create(
        @PathVariable final Integer projectId,
        @ModelAttribute("issueCreateVM") final IssueCreateVM vm) {
        vm.setProjectId(projectId);
        return "/issues/create";
    }

    @PostMapping("/issues")
    public String create(
        @PathVariable final Integer projectId,
        @ModelAttribute("issueCreateVM") @Validated final IssueCreateVM vm,
        final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/issues/create";
        }

        var request = new IssueCreateRequest(projectId, vm.getTitle(), vm.getDescription());
        var response = this.issueCreateUseCase.execute(request);

        return "redirect:/projects/" + projectId + "/issues/" + response.getId() + "/view";
    }

    @ModelAttribute("issueViewVM")
    private IssueViewVM createIssueViewVM() {
        return new IssueViewVM();
    }

    @GetMapping("/issues/{id}")
    public String view(
        @PathVariable final Integer projectId,
        @PathVariable final Integer id,
        @ModelAttribute("issueViewVM") final IssueViewVM vm) {

        var request = new IssueGetRequest(id);
        var response = this.issueGetUseCase.execute(request);
        var issue = response.getIssue();

        vm.setId(issue.getId());
        vm.setProjectId(issue.getProjectId());
        vm.setTitle(issue.getTitle());
        vm.setDescription(issue.getDescription());

        return "/issues/view";
    }

    @ModelAttribute("issueEditVM")
    private IssueEditVM createIssueEditVM() {
        return new IssueEditVM();
    }

    @GetMapping("/issues/{id}/edit")
    public String edit(
        @PathVariable final Integer projectId,
        @PathVariable final Integer id,
        @ModelAttribute("issueEditVM") final IssueEditVM vm) {

        var request = new IssueGetRequest(id);
        var response = this.issueGetUseCase.execute(request);
        var issue = response.getIssue();

        vm.setId(issue.getId());
        vm.setProjectId(issue.getProjectId());
        vm.setTitle(issue.getTitle());
        vm.setDescription(issue.getDescription());

        return "/issues/edit";
    }

    @PutMapping("/issues/{id}")
    public String update(
        @PathVariable final Integer projectId,
        @PathVariable final Integer id,
        @ModelAttribute("issueEditVM") @Validated final IssueEditVM vm,
        final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/issues/edit";
        }

        try {
            var request = new IssueUpdateRequest(id, vm.getTitle(), vm.getDescription());
            var response = this.issueUpdateUseCase.execute(request);

            return "redirect:/projects/" + projectId + "/issues/" + response.getId() + "/view";

        } catch (PersistenceException exception) {
            // NOTE: 楽観ロックに失敗したらもっかい編集画面を表示させる
            redirectAttributes.addFlashAttribute("alertMessage", exception.getMessage());

            return "redirect:/projects/" + projectId + "/issues/" + id + "/edit";
        }

    }

    @DeleteMapping("/issues/{id}")
    public String delete(
        @PathVariable final Integer projectId,
        @PathVariable final Integer id) {

        var request = new IssueDeleteRequest(id);
        this.issueDeleteUseCase.execute(request);

        return "redirect:/issues";
    }

}
