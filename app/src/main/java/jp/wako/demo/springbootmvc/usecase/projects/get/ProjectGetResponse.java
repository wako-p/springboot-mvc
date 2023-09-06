package jp.wako.demo.springbootmvc.usecase.projects.get;

import java.util.List;

import jp.wako.demo.springbootmvc.domain.issues.Issue;
import lombok.Data;

@Data
public class ProjectGetResponse {
    private final Integer id;
    private final String name;
    private final String description;
    private final List<Issue> issues;
}
