package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.detail;

import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.ProjectVM;
import lombok.Data;

@Data
public final class DetailVM {

    private ProjectVM project;
    private IssueVM issue;

    public DetailVM() {
        this.project = new ProjectVM("", "");
        this.issue = new IssueVM("", "", "");
    }

    public String getDescription() {
        return this.issue.getDescription()
            .replaceAll(System.lineSeparator(), "<br/>");
    }

}
