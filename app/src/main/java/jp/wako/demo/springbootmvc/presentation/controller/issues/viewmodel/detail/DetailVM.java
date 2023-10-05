package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.detail;

import lombok.Data;

@Data
public final class DetailVM {

    private DetailProjectVM project;
    private DetailIssueVM issue;

    public DetailVM() {
        this.project = new DetailProjectVM(0, "");
        this.issue = new DetailIssueVM(0, "", "");
    }

    public String getDescription() {
        return this.issue.getDescription()
            .replaceAll(System.lineSeparator(), "<br/>");
    }

}
