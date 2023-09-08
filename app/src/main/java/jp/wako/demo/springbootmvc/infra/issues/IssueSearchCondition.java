package jp.wako.demo.springbootmvc.infra.issues;

import jp.wako.demo.springbootmvc.infra.issues.sort.Sort;
import lombok.Data;

@Data
public final class IssueSearchCondition {
    private final Integer projectId;
    private final String title;
    private final Sort sort;
}
