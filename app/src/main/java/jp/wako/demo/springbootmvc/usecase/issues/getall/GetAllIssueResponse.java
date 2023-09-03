package jp.wako.demo.springbootmvc.usecase.issues.getall;

import java.util.List;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import lombok.Data;

@Data
public final class GetAllIssueResponse {
    private final List<Issue> issues;
}
