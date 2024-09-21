package jp.wako.demo.springbootmvc.presentation.controller.issues;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueSearchVM;
import jp.wako.demo.springbootmvc.presentation.shared.exception.ResourceNotFoundException;
import jp.wako.demo.springbootmvc.presentation.shared.helper.LongHelper;
import jp.wako.demo.springbootmvc.usecase.issues.search.IIssueSearchQuery;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchRequest;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}")
public class IssueSearchController {

    private final IIssueSearchQuery issueSearchQuery;

    @ModelAttribute("issueSearchVM")
    public IssueSearchVM createIssueSearchVM() {
        return new IssueSearchVM();
    }

    @GetMapping("/issues")
    public String index(
        @PathVariable final String projectId,
        @ModelAttribute("issueSearchVM") final IssueSearchVM vm) {

            if (LongHelper.unconvertible(projectId)) {
                throw new ResourceNotFoundException();
            }

            var issueSearchRequest = new IssueSearchRequest(Long.parseLong(projectId));
            var issueSearchResponse = this.issueSearchQuery.execute(issueSearchRequest);

            vm.loadFrom(issueSearchResponse);
            return "/issues/search";
    }

}
