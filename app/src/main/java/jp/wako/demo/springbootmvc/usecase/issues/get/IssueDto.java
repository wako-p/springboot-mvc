package jp.wako.demo.springbootmvc.usecase.issues.get;

import lombok.Data;

@Data
public final class IssueDto {
    private final Integer id;
    private final Integer projectId;
    private final String title;
    private final String description;
}
