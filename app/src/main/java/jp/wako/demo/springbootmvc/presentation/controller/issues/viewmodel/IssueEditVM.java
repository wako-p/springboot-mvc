package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jakarta.validation.Valid;
import jp.wako.demo.springbootmvc.usecase.issues.fetch.IssueFetchResponse;
import lombok.Data;

@Data
public final class IssueEditVM {

    private Project project;

    @Valid
    private Issue issue;

    public IssueEditVM() {
        this.project = new Project("", "");
        this.issue = new Issue("", "", "");
    }

        public void loadFrom(final IssueFetchResponse response) {
        this.project = Project.createFrom(response.getProject());
        this.issue = Issue.createFrom(response.getIssue());
    }

}
