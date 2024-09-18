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
import jp.wako.demo.springbootmvc.presentation.shared.exception.ResourceNotFoundException;
import jp.wako.demo.springbootmvc.presentation.shared.helper.LongHelper;
import jp.wako.demo.springbootmvc.usecase.issues.create.IssueCreateRequest;
import jp.wako.demo.springbootmvc.usecase.issues.create.IssueCreateUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.delete.IssueDeleteRequest;
import jp.wako.demo.springbootmvc.usecase.issues.delete.IssueDeleteUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.fetch.IssueFetchRequest;
import jp.wako.demo.springbootmvc.usecase.issues.fetch.IssueFetchUseCase;
import jp.wako.demo.springbootmvc.usecase.issues.update.IssueUpdateRequest;
import jp.wako.demo.springbootmvc.usecase.issues.update.IssueUpdateUseCase;
import jp.wako.demo.springbootmvc.usecase.projects.fetch.ProjectFetchRequest;
import jp.wako.demo.springbootmvc.usecase.projects.fetch.ProjectFetchUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}")
public class IssueDetailsController {

    private final ProjectFetchUseCase projectFetchUseCase;
    private final IssueFetchUseCase issueFetchUseCase;
    private final IssueCreateUseCase issueCreateUseCase;
    private final IssueUpdateUseCase issueUpdateUseCase;
    private final IssueDeleteUseCase issueDeleteUseCase;

    @ModelAttribute("issueCreateVM")
    private IssueCreateVM createIssueCreateVM() {
        return new IssueCreateVM();
    }

    @GetMapping("/issues/create")
    public String create(
        final @PathVariable String projectId,
        final @ModelAttribute("issueCreateVM") IssueCreateVM vm) {

            if (LongHelper.unconvertible(projectId)) {
                throw new ResourceNotFoundException();
            }

            var projectFetchRequest = new ProjectFetchRequest(Long.parseLong(projectId));
            var projectFetchResponse = this.projectFetchUseCase.execute(projectFetchRequest);

            vm.loadFrom(projectFetchResponse);
            return "/issues/create";
    }

    @PostMapping("/issues")
    public String create(
        final @PathVariable String projectId,
        final @ModelAttribute("issueCreateVM") @Validated IssueCreateVM vm,
        final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes) {

            if (LongHelper.unconvertible(projectId)) {
                throw new ResourceNotFoundException();
            }

            if (bindingResult.hasErrors()) {
                return "/issues/create";
            }

            try {
                var issueCreateRequest = new IssueCreateRequest(
                    Long.parseLong(projectId),
                    vm.getIssue().getTitle(),
                    vm.getIssue().getDescription());
    
                var issueCreateResponse = this.issueCreateUseCase.execute(issueCreateRequest);
    
                return "redirect:/projects/" + issueCreateResponse.getProjectId() + "/issues/" + issueCreateResponse.getId();

            } catch (PersistenceException exception) {
                redirectAttributes.addFlashAttribute("alertMessage", exception.getMessage());
                return "redirect:/projects/" + projectId + "/issues/create";
            }
    }

    @ModelAttribute("issueDetailVM")
    private IssueDetailVM createIssueDetailVM() {
        return new IssueDetailVM();
    }

    @GetMapping("/issues/{issueId}")
    public String detail(
        final @PathVariable String projectId,
        final @PathVariable String issueId,
        final @ModelAttribute("issueDetailVM") IssueDetailVM vm) {

            if (LongHelper.unconvertible(projectId) || LongHelper.unconvertible(issueId)) {
                throw new ResourceNotFoundException();
            }

            var projectFetchRequest = new ProjectFetchRequest(Long.parseLong(projectId));
            var projectFetchResponse = this.projectFetchUseCase.execute(projectFetchRequest);

            var issueFetchRequest = new IssueFetchRequest(Long.parseLong(issueId));
            var issueFetchResponse = this.issueFetchUseCase.execute(issueFetchRequest);

            vm.loadFrom(projectFetchResponse, issueFetchResponse);
            return "/issues/detail";
    }

    @ModelAttribute("issueEditVM")
    private IssueEditVM createIssueEditVM() {
        return new IssueEditVM();
    }

    @GetMapping("/issues/{issueId}/edit")
    public String edit(
        final @PathVariable String projectId,
        final @PathVariable String issueId,
        final @ModelAttribute("issueEditVM") IssueEditVM vm) {

            if (LongHelper.unconvertible(projectId) || LongHelper.unconvertible(issueId)) {
                throw new ResourceNotFoundException();
            }

            var projectFetchRequest = new ProjectFetchRequest(Long.parseLong(projectId));
            var projectFetchResponse = this.projectFetchUseCase.execute(projectFetchRequest);

            var issueFetchRequest = new IssueFetchRequest(Long.parseLong(issueId));
            var issueFetchResponse = this.issueFetchUseCase.execute(issueFetchRequest);

            vm.loadFrom(projectFetchResponse, issueFetchResponse);
            return "/issues/edit";
    }

    @PutMapping("/issues/{issueId}")
    public String update(
        final @PathVariable String projectId,
        final @PathVariable String issueId,
        final @ModelAttribute("issueEditVM") @Validated IssueEditVM vm,
        final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes) {

            if (LongHelper.unconvertible(projectId) || LongHelper.unconvertible(issueId)) {
                throw new ResourceNotFoundException();
            }

            if (bindingResult.hasErrors()) {
                return "/issues/edit";
            }

            try {
                var request = new IssueUpdateRequest(
                    Long.parseLong(issueId),
                    Long.parseLong(projectId),
                    vm.getIssue().getTitle(),
                    vm.getIssue().getDescription());

                var response = this.issueUpdateUseCase.execute(request);

                return "redirect:/projects/" + response.getProjectId() + "/issues/" + response.getId();

            } catch (PersistenceException exception) {
                redirectAttributes.addFlashAttribute("alertMessage", exception.getMessage());
                return "redirect:/projects/" + projectId + "/issues/" + issueId + "/edit";
            }
    }

    @DeleteMapping("/issues/{issueId}")
    public String delete(
        final @PathVariable String projectId,
        final @PathVariable String issueId) {

            if (LongHelper.unconvertible(projectId) || LongHelper.unconvertible(issueId)) {
                throw new ResourceNotFoundException();
            }

            var request = new IssueDeleteRequest(Long.parseLong(issueId));
            this.issueDeleteUseCase.execute(request);

            return "redirect:/issues";
    }

}
