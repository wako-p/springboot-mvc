package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jp.wako.demo.springbootmvc.usecase.issues.search.IssueDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class IssueSearchResult {

    private String id;
    private String projectId;
    private String title;
    private String description;

    public static IssueSearchResult create(final IssueDto issue) {
        return new IssueSearchResult(
            issue.getId().toString(),
            issue.getProjectId().toString(),
            issue.getTitle(),
            issue.getDescription());
    }

}
