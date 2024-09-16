package jp.wako.demo.springbootmvc.presentation.controller.issues;

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
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueDetailVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueEditVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.Issue;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.Project;
import jp.wako.demo.springbootmvc.usecase.issues.create.IssueCreateRequest;
import jp.wako.demo.springbootmvc.usecase.issues.create.IssueCreateUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.delete.IssueDeleteRequest;
import jp.wako.demo.springbootmvc.usecase.issues.delete.IssueDeleteUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.get.IssueGetRequest;
import jp.wako.demo.springbootmvc.usecase.issues.get.IssueGetUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.update.IssueUpdateRequest;
import jp.wako.demo.springbootmvc.usecase.issues.update.IssueUpdateUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}")
public class IssueDetailsController {

    private final IssueGetUseCase issueGetUseCase;
    private final IssueCreateUseCase issueCreateUseCase;
    private final IssueUpdateUseCase issueUpdateUseCase;
    private final IssueDeleteUseCase issueDeleteUseCase;

    @ModelAttribute("createVM")
    private IssueCreateVM createCreateVM() {
        return new IssueCreateVM();
    }

    @GetMapping("/issues/create")
    public String create(
        @PathVariable final Integer projectId,
        @ModelAttribute("createVM") final IssueCreateVM vm) {
        vm.getProject().setId(projectId.toString());
        return "/issues/create";
    }

    @PostMapping("/issues")
    public String create(
        @PathVariable final Integer projectId,
        @ModelAttribute("createVM") @Validated final IssueCreateVM vm,
        final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/issues/create";
        }

        var request = new IssueCreateRequest(projectId, vm.getIssue().getTitle(), vm.getIssue().getDescription());
        var response = this.issueCreateUseCase.execute(request);

        return "redirect:/projects/" + projectId + "/issues/" + response.getId();
    }

    @ModelAttribute("detailVM")
    private IssueDetailVM createDetailVM() {
        return new IssueDetailVM();
    }

    @GetMapping("/issues/{id}")
    public String detail(
        @PathVariable final Integer projectId,
        @PathVariable final Integer id,
        @ModelAttribute("detailVM") final IssueDetailVM vm) {

        var request = new IssueGetRequest(projectId, id);
        var response = this.issueGetUseCase.execute(request);

        var projectDto = response.getProject();
        var issueDto = response.getIssue();

        var projectVM = Project.createFrom(projectDto);
        var issueVM = Issue.createFrom(issueDto);

        vm.setProject(projectVM);
        vm.setIssue(issueVM);

        return "/issues/detail";
    }

    @ModelAttribute("editVM")
    private IssueEditVM createEditVM() {
        return new IssueEditVM();
    }

    @GetMapping("/issues/{id}/edit")
    public String edit(
        @PathVariable final Integer projectId,
        @PathVariable final Integer id,
        @ModelAttribute("editVM") final IssueEditVM vm) {

        var request = new IssueGetRequest(projectId, id);
        var response = this.issueGetUseCase.execute(request);

        var projectDto = response.getProject();
        var issueDto = response.getIssue();

        var projectVM = Project.createFrom(projectDto);
        var issueVM = Issue.createFrom(issueDto);

        vm.setProject(projectVM);
        vm.setIssue(issueVM);

        return "/issues/edit";
    }

    @PutMapping("/issues/{id}")
    public String update(
        @ModelAttribute("editVM") @Validated final IssueEditVM vm,
        final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/issues/edit";
        }

        try {
            // TODO: projectIdもリクエストに含める？
            var request = new IssueUpdateRequest(
                Integer.parseInt(vm.getIssue().getId()),
                vm.getIssue().getTitle(),
                vm.getIssue().getDescription());

            var response = this.issueUpdateUseCase.execute(request);

            return "redirect:/projects/" + vm.getProject().getId() + "/issues/" + response.getId();

        } catch (PersistenceException exception) {
            // NOTE: 楽観ロックに失敗したらもっかい編集画面を表示させる
            redirectAttributes.addFlashAttribute("alertMessage", exception.getMessage());
            return "redirect:/projects/" + vm.getProject().getId() + "/issues/" + vm.getIssue().getId() + "/edit";
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
