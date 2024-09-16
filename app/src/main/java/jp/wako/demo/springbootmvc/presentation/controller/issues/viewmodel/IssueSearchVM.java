package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchResponse;
import lombok.Data;

@Data
public final class IssueSearchVM {

    private Project project;
    private List<Issue> issues;

    public IssueSearchVM() {
        this.project = new Project("", "");
        this.issues = new ArrayList<>();
    }

    public void loadFrom(final IssueSearchResponse response) {
    
        var project = Project.createFrom(response.getProject());
        var issues = response.getIssues()
            .stream()
            .map(Issue::createFrom)
            .collect(Collectors.toList());

        this.project = project;
        this.issues = issues;
    }

}
