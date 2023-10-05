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
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.detail.DetailVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.edit.EditIssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.edit.EditProjectVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.edit.EditVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.create.IssueCreateVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.detail.DetailIssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.detail.DetailProjectVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.index.IndexIssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.index.IndexProjectVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.index.IndexVM;
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

    @ModelAttribute("indexVM")
    public IndexVM createIndexVM() {
        return new IndexVM();
    }

    @GetMapping("/issues")
    public String index(
        @PathVariable final Integer projectId,
        @ModelAttribute("indexVM") final IndexVM vm) {

        // TODO: UseCaseException補足する
        var request = new IssueSearchRequest(projectId);
        var response = this.issueSearchQuery.execute(request);

        var project = IndexProjectVM.createFrom(response.getProject());
        var issues = response.getIssues()
            .stream()
            .map(IndexIssueVM::createFrom)
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

        return "redirect:/projects/" + projectId + "/issues/" + response.getId();
    }

    @ModelAttribute("detailVM")
    private DetailVM createDetailVM() {
        return new DetailVM();
    }

    @GetMapping("/issues/{id}")
    public String detail(
        @PathVariable final Integer projectId,
        @PathVariable final Integer id,
        @ModelAttribute("detailVM") final DetailVM vm) {

        var request = new IssueGetRequest(projectId, id);
        var response = this.issueGetUseCase.execute(request);

        var projectDto = response.getProject();
        var issueDto = response.getIssue();

        var projectVM = DetailProjectVM.createFrom(projectDto);
        var issueVM = DetailIssueVM.createFrom(issueDto);

        vm.setProject(projectVM);
        vm.setIssue(issueVM);

        return "/issues/detail";
    }

    @ModelAttribute("editVM")
    private EditVM creatEditVM() {
        return new EditVM();
    }

    @GetMapping("/issues/{id}/edit")
    public String edit(
        @PathVariable final Integer projectId,
        @PathVariable final Integer id,
        @ModelAttribute("editVM") final EditVM vm) {

        var request = new IssueGetRequest(projectId, id);
        var response = this.issueGetUseCase.execute(request);

        var projectDto = response.getProject();
        var issueDto = response.getIssue();

        var projectVM = EditProjectVM.createFrom(projectDto);
        var issueVM = EditIssueVM.createFrom(issueDto);

        vm.setProject(projectVM);
        vm.setIssue(issueVM);

        return "/issues/edit";
    }

    @PutMapping("/issues/{id}")
    public String update(
        @PathVariable final Integer projectId,
        @PathVariable final Integer id,
        @ModelAttribute("editVM") @Validated final EditVM vm,
        final BindingResult bindingResult,
        final RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            // TODO: バリデーションエラーのあとに[Cancel]ボタンクリックすると400エラーになる
            return "/issues/edit";
        }

        try {
            // TODO: projectIdもリクエストに含める
            var request = new IssueUpdateRequest(
                id,
                vm.getIssue().getTitle(),
                vm.getIssue().getDescription());

            var response = this.issueUpdateUseCase.execute(request);

            return "redirect:/projects/" + projectId + "/issues/" + response.getId();

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
