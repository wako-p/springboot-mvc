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

    @ModelAttribute("issueCreateVM")
    private IssueCreateVM createIssueCreateVM() {
        return new IssueCreateVM();
    }

    @GetMapping("/issues/create")
    public String create(
        @PathVariable final Long projectId,
        @ModelAttribute("issueCreateVM") final IssueCreateVM vm) {
        vm.getProject().setId(projectId.toString());
        return "/issues/create";
    }

    @PostMapping("/issues")
    public String create(
        @PathVariable final Long projectId,
        @ModelAttribute("issueCreateVM") @Validated final IssueCreateVM vm,
        final BindingResult bindingResult) {

        // TODO: projectIdが数値に変換できるかどうかを判定する

        if (bindingResult.hasErrors()) {
            return "/issues/create";
        }

        var request = new IssueCreateRequest(projectId, vm.getIssue().getTitle(), vm.getIssue().getDescription());
        var response = this.issueCreateUseCase.execute(request);

        return "redirect:/projects/" + response.getProjectId() + "/issues/" + response.getId();
    }

    @ModelAttribute("issueDetailVM")
    private IssueDetailVM createIssueDetailVM() {
        return new IssueDetailVM();
    }

    @GetMapping("/issues/{issueId}")
    public String detail(
        @PathVariable final Long projectId,
        @PathVariable final Long issueId,
        @ModelAttribute("issueDetailVM") final IssueDetailVM vm) {

        // TODO: projectIdが数値に変換できるかどうかを判定する

        var request = new IssueGetRequest(projectId, issueId);
        var response = this.issueGetUseCase.execute(request);

        vm.loadFrom(response);

        return "/issues/detail";
    }

    @ModelAttribute("issueEditVM")
    private IssueEditVM createIssueEditVM() {
        return new IssueEditVM();
    }

    @GetMapping("/issues/{issueId}/edit")
    public String edit(
        @PathVariable final Long projectId,
        @PathVariable final Long issueId,
        @ModelAttribute("issueEditVM") final IssueEditVM vm) {

        // TODO: projectIdが数値に変換できるかどうかを判定する

        var request = new IssueGetRequest(projectId, issueId);
        var response = this.issueGetUseCase.execute(request);

        vm.loadFrom(response);

        return "/issues/edit";
    }

    @PutMapping("/issues/{issueId}")
    public String update(
        @ModelAttribute("issueEditVM") @Validated final IssueEditVM vm,
        final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes) {

        // TODO: projectIdが数値に変換できるかどうかを判定する

        if (bindingResult.hasErrors()) {
            return "/issues/edit";
        }

        try {
            var request = new IssueUpdateRequest(
                Long.parseLong(vm.getIssue().getId()),
                Long.parseLong(vm.getProject().getId()),
                vm.getIssue().getTitle(),
                vm.getIssue().getDescription());

            var response = this.issueUpdateUseCase.execute(request);

            return "redirect:/projects/" + response.getProjectId() + "/issues/" + response.getId();

        } catch (PersistenceException exception) {
            // NOTE: 楽観ロックに失敗したらもっかい編集画面を表示させる
            redirectAttributes.addFlashAttribute("alertMessage", exception.getMessage());
            return "redirect:/projects/" + vm.getProject().getId() + "/issues/" + vm.getIssue().getId() + "/edit";
        }

    }

    @DeleteMapping("/issues/{issueId}")
    public String delete(
        @PathVariable final Long projectId,
        @PathVariable final Long issueId) {

        // TODO: projectIdが数値に変換できるかどうかを判定する

        var request = new IssueDeleteRequest(issueId);
        this.issueDeleteUseCase.execute(request);

        return "redirect:/issues";
    }

}
