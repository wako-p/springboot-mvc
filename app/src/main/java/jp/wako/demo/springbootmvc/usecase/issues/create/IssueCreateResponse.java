package jp.wako.demo.springbootmvc.usecase.issues.create;

import lombok.Data;

@Data
public final class IssueCreateResponse {
    private final Long id;
    private final Long projectId;
    private final String title;
    private final String description;
}
