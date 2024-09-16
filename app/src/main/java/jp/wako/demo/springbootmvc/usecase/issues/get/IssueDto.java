package jp.wako.demo.springbootmvc.usecase.issues.get;

import lombok.Data;

@Data
public final class IssueDto {
    private final Long id;
    private final String title;
    private final String description;
}
