package jp.wako.demo.springbootmvc.presentation.controller.issues;

import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueSearchVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.Issue;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.Project;
import jp.wako.demo.springbootmvc.usecase.issues.search.IIssueSearchQuery;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}")
public class IssueSearchController {

    private final IIssueSearchQuery issueSearchQuery;

    @ModelAttribute("indexVM")
    public IssueSearchVM createIndexVM() {
        return new IssueSearchVM();
    }

    @GetMapping("/issues")
    public String index(
        @PathVariable final Integer projectId,
        @ModelAttribute("indexVM") final IssueSearchVM vm) {

        // TODO: UseCaseException補足する
        var request = new IssueSearchRequest(projectId);
        var response = this.issueSearchQuery.execute(request);

        var project = Project.createFrom(response.getProject());
        var issues = response.getIssues()
            .stream()
            .map(Issue::createFrom)
            .collect(Collectors.toList());

        vm.setProject(project);
        vm.setIssues(issues);

        return "/issues/index";
    }

}
