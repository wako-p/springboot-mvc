package jp.wako.demo.springbootmvc.usecase.issues.get;

import lombok.Data;

@Data
public final class GetIssueResponse {
    private final Integer id;
    private final Integer projectId;
    private final String title;
    private final String description;
    private final Integer version;
}
