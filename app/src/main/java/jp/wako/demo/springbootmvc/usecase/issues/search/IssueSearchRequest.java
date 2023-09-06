package jp.wako.demo.springbootmvc.usecase.issues.search;

import lombok.Data;

@Data
public class IssueSearchRequest {
    private final Integer projectId;
    private final String title;
    private final String sort;
    private final String order;

    public IssueSearchRequest(final Integer projectId) {
        this.projectId = projectId;
        this.title = "";
        this.sort = "updated_at";
        this.order = "desc";
    }

    public IssueSearchRequest(final Integer projectId, final String title) {
        this.projectId = projectId;
        this.title = title;
        this.sort = "updated_at";
        this.order = "desc";
    }

}
