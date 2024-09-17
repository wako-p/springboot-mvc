package jp.wako.demo.springbootmvc.usecase.issues.fetch;

import lombok.Data;

@Data
public final class IssueFetchResponse {
    private final ProjectDto project;
    private final IssueDto issue;
}
