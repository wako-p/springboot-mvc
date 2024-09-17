package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchResponse;
import jp.wako.demo.springbootmvc.usecase.projects.fetch.ProjectFetchResponse;
import lombok.Data;

@Data
public final class IssueSearchVM {

    private Project project;
    private List<Issue> issues;

    public IssueSearchVM() {
        this.project = new Project("", "");
        this.issues = new ArrayList<>();
    }

    public void loadFrom(
        final ProjectFetchResponse projectFetchResponse,
        final IssueSearchResponse issueSearchResponse) {
            this.project = Project.create(projectFetchResponse);
            this.issues = issueSearchResponse.getIssues()
                .stream()
                .map(Issue::create)
                .collect(Collectors.toList());
    }

}
