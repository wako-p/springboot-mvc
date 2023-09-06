package jp.wako.demo.springbootmvc.usecase.issues.search;

import java.util.List;

import lombok.Data;

@Data
public class IssueSearchResponse {
    private final List<IssueDto> issues;
}
