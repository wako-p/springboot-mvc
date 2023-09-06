package jp.wako.demo.springbootmvc.usecase.issues.search;

import lombok.Data;

@Data
public class IssueSearchRequest {

    private final Integer projectId;
    private final String title;
    private final String sortColumn;
    private final String sortOrder;

    public IssueSearchRequest(final Integer projectId) {
        this.projectId = projectId;
        this.title = "";
        this.sortColumn = "updated_at";
        this.sortOrder = "desc";
    }

    public IssueSearchRequest(final Integer projectId, final String title) {
        this.projectId = projectId;
        this.title = title;
        this.sortColumn = "updated_at";
        this.sortOrder = "desc";
    }

}
