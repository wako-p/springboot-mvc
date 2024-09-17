package jp.wako.demo.springbootmvc.presentation.controller.issues;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueSearchVM;
import jp.wako.demo.springbootmvc.usecase.issues.search.IIssueSearchQuery;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchRequest;
import jp.wako.demo.springbootmvc.usecase.projects.fetch.ProjectFetchRequest;
import jp.wako.demo.springbootmvc.usecase.projects.fetch.ProjectFetchUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}")
public class IssueSearchController {

    private final IIssueSearchQuery issueSearchQuery;
    private final ProjectFetchUseCase projectFetchUseCase;

    @ModelAttribute("issueSearchVM")
    public IssueSearchVM createIssueSearchVM() {
        return new IssueSearchVM();
    }

    @GetMapping("/issues")
    public String index(
        @PathVariable final Long projectId,
        @ModelAttribute("issueSearchVM") final IssueSearchVM vm) {

            // TODO: projectIdの型をStringにする
            // TODO: projectIdが数値に変換できるかどうかを判定する

            var projectFetchRequest = new ProjectFetchRequest(projectId);
            var projectFetchResponse = this.projectFetchUseCase.execute(projectFetchRequest);

            var issueSearchRequest = new IssueSearchRequest(projectId);
            var issueSearchResponse = this.issueSearchQuery.execute(issueSearchRequest);

            vm.loadFrom(projectFetchResponse, issueSearchResponse);
            return "/issues/search";
    }

}
