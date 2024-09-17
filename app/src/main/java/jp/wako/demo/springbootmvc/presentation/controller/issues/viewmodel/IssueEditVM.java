package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jakarta.validation.Valid;
import jp.wako.demo.springbootmvc.usecase.issues.fetch.IssueFetchResponse;
import jp.wako.demo.springbootmvc.usecase.projects.fetch.ProjectFetchResponse;
import lombok.Data;

@Data
public final class IssueEditVM {

    private Project project;

    @Valid
    private Issue issue;

    public IssueEditVM() {
        this.project = new Project("", "");
        this.issue = new Issue("", "", "", "");
    }

    public void loadFrom(
        final ProjectFetchResponse projectFetchResponse,
        final IssueFetchResponse issueFetchResponse) {
            this.project = Project.create(projectFetchResponse);
            this.issue = Issue.create(issueFetchResponse);
    }

}
