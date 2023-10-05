package jp.wako.demo.springbootmvc.usecase.issues.get;

import lombok.Data;

@Data
public final class IssueGetResponse {
    private final ProjectDto project;
    private final IssueDto issue;
}
