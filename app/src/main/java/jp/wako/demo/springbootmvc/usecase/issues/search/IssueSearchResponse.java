package jp.wako.demo.springbootmvc.usecase.issues.search;

import java.util.List;

import lombok.Data;

@Data
public class IssueSearchResponse {
    private final ProjectDto project;
    private final List<IssueDto> issues;
}
