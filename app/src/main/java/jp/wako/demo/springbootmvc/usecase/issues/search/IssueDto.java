package jp.wako.demo.springbootmvc.usecase.issues.search;

import lombok.Data;

@Data
public final class IssueDto {
    private final Long id;
    private final Long projectId;
    private final String title;
    private final String description;
}
