package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jp.wako.demo.springbootmvc.usecase.issues.fetch.IssueFetchResponse;
import jp.wako.demo.springbootmvc.usecase.projects.fetch.ProjectFetchResponse;
import lombok.Data;

@Data
public final class IssueDetailVM {

    private Project project;
    private Issue issue;

    public IssueDetailVM() {
        this.project = new Project("", "");
        this.issue = new Issue("", "", "", "");
    }

    public void loadFrom(
        final ProjectFetchResponse projectFetchResponse,
        final IssueFetchResponse issueFetchResponse) {
            this.project = Project.create(projectFetchResponse);
            this.issue = Issue.create(issueFetchResponse);
    }

    public String getDescription() {
        return this.issue.getDescription()
            .replaceAll(System.lineSeparator(), "<br/>");
    }

}
