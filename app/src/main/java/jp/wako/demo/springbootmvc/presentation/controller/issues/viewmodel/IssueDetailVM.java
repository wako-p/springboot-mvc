package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import lombok.Data;

@Data
public final class IssueDetailVM {

    private Project project;
    private Issue issue;

    public IssueDetailVM() {
        this.project = new Project("", "");
        this.issue = new Issue("", "", "");
    }

    public String getDescription() {
        return this.issue.getDescription()
            .replaceAll(System.lineSeparator(), "<br/>");
    }

}
