package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jp.wako.demo.springbootmvc.usecase.issues.get.IssueGetResponse;
import lombok.Data;

@Data
public final class IssueDetailVM {

    private Project project;
    private Issue issue;

    public IssueDetailVM() {
        this.project = new Project("", "");
        this.issue = new Issue("", "", "");
    }

    public void loadFrom(final IssueGetResponse response) {
        this.project = Project.createFrom(response.getProject());
        this.issue = Issue.createFrom(response.getIssue());
    }

    public String getDescription() {
        return this.issue.getDescription()
            .replaceAll(System.lineSeparator(), "<br/>");
    }

}
