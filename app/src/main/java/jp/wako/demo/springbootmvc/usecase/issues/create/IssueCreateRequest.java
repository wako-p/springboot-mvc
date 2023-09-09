package jp.wako.demo.springbootmvc.usecase.issues.create;

import lombok.Data;

@Data
public final class IssueCreateRequest {
    private final Integer projectId;
    private final String title;
    private final String description;
}
