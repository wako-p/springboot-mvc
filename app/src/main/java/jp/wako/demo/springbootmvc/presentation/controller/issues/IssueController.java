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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.wako.demo.springbootmvc.infra.shared.exception.PersistenceException;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueEditVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueIndexVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueViewVM;
import jp.wako.demo.springbootmvc.usecase.issues.create.CreateIssueRequest;
import jp.wako.demo.springbootmvc.usecase.issues.create.CreateIssueUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.delete.DeleteIssueRequest;
import jp.wako.demo.springbootmvc.usecase.issues.delete.DeleteIssueUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.get.GetIssueRequest;
import jp.wako.demo.springbootmvc.usecase.issues.get.GetIssueUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.getall.GetAllIssueRequest;
import jp.wako.demo.springbootmvc.usecase.issues.getall.GetAllIssueUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.update.UpdateIssueRequest;
import jp.wako.demo.springbootmvc.usecase.issues.update.UpdateIssueUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class IssueController {

    private final GetAllIssueUseCase getAllIssueUseCase;
    private final GetIssueUseCase getIssueUseCase;
    private final CreateIssueUseCase createIssueUseCase;
    private final UpdateIssueUseCase updateIssueUseCase;
    private final DeleteIssueUseCase deleteIssueUseCase;

    @ModelAttribute("issueIndexVM")
    private IssueIndexVM createIssueIndexVM() {
        return new IssueIndexVM();
    }

    @GetMapping("/issues")
    public String index(
        @ModelAttribute("issueIndexVM") final IssueIndexVM vm) {

        var response = this.getAllIssueUseCase.execute(new GetAllIssueRequest());
        var issues = response.getIssues()
            .stream()
            .map(issue -> new IssueVM(
                issue.getId(),
                issue.getTitle(),
                issue.getDescription(),
                issue.getCreatedAt(),
                issue.getUpdatedAt()))
            .collect(Collectors.toList());

        vm.setIssues(issues);

        return "/issues/list";
    }

    @ModelAttribute("issueViewVM")
    private IssueViewVM createIssueViewVM() {
        return new IssueViewVM();
    }

    @GetMapping("/issues/{id}/view")
    public String view(
        @PathVariable final Integer id,
        @ModelAttribute("issueViewVM") final IssueViewVM vm) {

        var request = new GetIssueRequest(id);
        var response = this.getIssueUseCase.execute(request);

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

        var request = new GetIssueRequest(id);
        var response = this.getIssueUseCase.execute(request);

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
            return index(vm);
        }

        var issueCreateVM = vm.getIssueCreateVM();
        var request = new CreateIssueRequest(issueCreateVM.getTitle());
        this.createIssueUseCase.execute(request);

        return "redirect:/issues";
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
            var request = new UpdateIssueRequest(id, vm.getTitle(), vm.getDescription(), vm.getVersion());
            var response = this.updateIssueUseCase.execute(request);

            return "redirect:/issues/" + response.getId() + "/view";

        } catch (PersistenceException exception) {
            // NOTE: 楽観ロックに失敗したらもっかい編集画面を表示させる
            redirectAttributes.addFlashAttribute("alertMessage", exception.getMessage());

            return "redirect:/issues/" + id + "/edit";
        }

    }

    @DeleteMapping("/issues/{id}")
    public String delete(@PathVariable final Integer id) {

        var request = new DeleteIssueRequest(id);
        this.deleteIssueUseCase.execute(request);

        return "redirect:/issues";
    }

}
