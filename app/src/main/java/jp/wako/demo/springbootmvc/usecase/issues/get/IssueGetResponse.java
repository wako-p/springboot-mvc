package jp.wako.demo.springbootmvc.usecase.issues.get;

import lombok.Data;

@Data
public final class IssueGetResponse {
    private final IssueDto issue;
}
