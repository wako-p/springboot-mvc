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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueEditVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueIndexVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueViewVM;
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
public class IssueController {

    private final IssueGetUseCase issueGetUseCase;
    private final IssueCreateUseCase issueCreateUseCase;
    private final IssueUpdateUseCase issueUpdateUseCase;
    private final IssueDeleteUseCase issueDeleteUseCase;

    @ModelAttribute("issueIndexVM")
    private IssueIndexVM createIssueIndexVM() {
        return new IssueIndexVM();
    }

    @ModelAttribute("issueViewVM")
    private IssueViewVM createIssueViewVM() {
        return new IssueViewVM();
    }

    @GetMapping("/issues/{id}/view")
    public String view(
        @PathVariable final Integer id,
        @ModelAttribute("issueViewVM") final IssueViewVM vm) {

        var request = new IssueGetRequest(id);
        var response = this.issueGetUseCase.execute(request);

        vm.setId(response.getId());
        vm.setProjectId(response.getProjectId());
        vm.setTitle(response.getTitle());
        vm.setDescription(response.getDescription());

        return "/issues/view";
    }

    @ModelAttribute("issueEditVM")
    private IssueEditVM createIssueEditVM() {
        return new IssueEditVM();
    }

    @GetMapping("/issues/{id}/edit")
    public String edit(
        @PathVariable final Integer id,
        @ModelAttribute("issueEditVM") final IssueEditVM vm) {

        var request = new IssueGetRequest(id);
        var response = this.issueGetUseCase.execute(request);

        vm.setId(response.getId());
        vm.setTitle(response.getTitle());
        vm.setDescription(response.getDescription());
        vm.setVersion(response.getVersion());

        return "/issues/edit";
    }

    @PostMapping("/issues")
    public String create(
        @ModelAttribute("issueIndexVM") @Validated final IssueIndexVM vm,
        final BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            // TODO: 画面(テンプレート)つくる
            return "/issues/create";
        }

        var issueCreateVM = vm.getIssueCreateVM();
        var request = new IssueCreateRequest(issueCreateVM.getTitle());
        var response = this.issueCreateUseCase.execute(request);

        return "redirect:/issues/" + response.getId() + "/view";
    }

    @PutMapping("/issues/{id}")
    public String update(
        @PathVariable final Integer id,
        @ModelAttribute("issueEditVM") @Validated final IssueEditVM vm,
        final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "/issues/edit";
        }

        try {
            var request = new IssueUpdateRequest(id, vm.getTitle(), vm.getDescription(), vm.getVersion());
            var response = this.issueUpdateUseCase.execute(request);

            return "redirect:/issues/" + response.getId() + "/view";

        } catch (PersistenceException exception) {
            // NOTE: 楽観ロックに失敗したらもっかい編集画面を表示させる
            redirectAttributes.addFlashAttribute("alertMessage", exception.getMessage());

            return "redirect:/issues/" + id + "/edit";
        }

    }

    @DeleteMapping("/issues/{id}")
    public String delete(@PathVariable final Integer id) {

        var request = new IssueDeleteRequest(id);
        this.issueDeleteUseCase.execute(request);

        return "redirect:/issues";
    }

}