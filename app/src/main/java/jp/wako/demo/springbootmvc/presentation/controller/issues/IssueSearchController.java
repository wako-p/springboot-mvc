package jp.wako.demo.springbootmvc.presentation.controller.issues;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueSearchVM;
import jp.wako.demo.springbootmvc.presentation.shared.exception.ResourceNotFoundException;
import jp.wako.demo.springbootmvc.presentation.shared.helper.LongHelper;
import jp.wako.demo.springbootmvc.usecase.issues.search.IIssueSearchQuery;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;




@RequiredArgsConstructor
@Controller
@RequestMapping("/projects/{projectId}/issues")
@SessionAttributes("issueSearchVM")
public class IssueSearchController {

    private final IIssueSearchQuery issueSearchQuery;

    @ModelAttribute("issueSearchVM")
    public IssueSearchVM createIssueSearchVM() {
        return new IssueSearchVM();
    }

    @GetMapping({"", "/"})
    public String index(
        final @PathVariable String projectId,
        final @ModelAttribute("issueSearchVM") IssueSearchVM vm,
        final @PageableDefault(5) Pageable pageable) {

            if (LongHelper.unconvertible(projectId)) {
                throw new ResourceNotFoundException();
            }

            var request = new IssueSearchRequest(Long.parseLong(projectId));
            var response = this.issueSearchQuery.execute(request);

            vm.loadFrom(response, pageable);
            return "/issues/search";
    }

    @GetMapping("/search")
    public String search(
        final @PathVariable String projectId,
        final @ModelAttribute("issueSearchVM") IssueSearchVM vm,
        final @PageableDefault(5) Pageable pageable) {

            if (LongHelper.unconvertible(projectId)) {
                throw new ResourceNotFoundException();
            }

            var request = new IssueSearchRequest(Long.parseLong(projectId), vm.getParameter().getTitle());
            var response = this.issueSearchQuery.execute(request);

            vm.loadFrom(response, pageable);
            return "/issues/search";
    }

    @GetMapping("/paging/{page}/{size}")
    public String paging(
        final @PathVariable String projectId,
        final @PathVariable String page,
        final @PathVariable String size,
        final @RequestParam MultiValueMap<String, String> requestParameter,
        final RedirectAttributes redirectAttributes) {

            requestParameter.set("page", page);
            requestParameter.set("size", size);

            redirectAttributes.addAllAttributes(requestParameter);
            return "redirect:/projects/" + projectId + "/issues/search";
    }

    @GetMapping("/back")
    public String back(
        final @PathVariable String projectId,
        final @ModelAttribute("issueSearchVM") IssueSearchVM vm,
        final RedirectAttributes redirectAttributes) {

            if (LongHelper.unconvertible(projectId)) {
                throw new ResourceNotFoundException();
            }

            var requestParameter = new LinkedMultiValueMap<String, Object>() {{
                set("page", vm.getPage());
                set("size", vm.getSize());
            }};

            redirectAttributes.addAllAttributes(requestParameter);
            return "redirect:/projects/" + projectId + "/issues/search";
    }

}
