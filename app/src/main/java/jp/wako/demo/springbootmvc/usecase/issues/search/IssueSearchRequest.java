package jp.wako.demo.springbootmvc.usecase.issues.search;

import lombok.Data;

@Data
public class IssueSearchRequest {
    private final Integer projectId;
}
