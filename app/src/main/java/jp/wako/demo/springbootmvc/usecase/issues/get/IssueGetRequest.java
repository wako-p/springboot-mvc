package jp.wako.demo.springbootmvc.usecase.issues.get;

import lombok.Data;

@Data
public final class IssueGetRequest {
    private final Long projectId;
    private final Long id;
}
