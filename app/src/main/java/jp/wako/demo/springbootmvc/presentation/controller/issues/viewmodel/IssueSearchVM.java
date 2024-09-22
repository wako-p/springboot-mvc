package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchResponse;
import lombok.Data;

@Data
public final class IssueSearchVM {

    private Project project;
    private IssueSearchParameter parameter;
    private List<IssueSearchResult> results;

    public IssueSearchVM() {
        this.project = new Project("", "");
        this.parameter = new IssueSearchParameter("");
        this.results = new ArrayList<>();
    }

    public void loadFrom(final IssueSearchResponse issueSearchResponse) {
            this.project = Project.create(issueSearchResponse.getProject());
            this.results = issueSearchResponse.getIssues()
                .stream()
                .map(IssueSearchResult::create)
                .collect(Collectors.toList());
    }

}
