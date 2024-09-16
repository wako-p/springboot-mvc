package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.detail;

import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import lombok.Data;

@Data
public final class DetailVM {

    private DetailProjectVM project;
    private IssueVM issue;

    public DetailVM() {
        this.project = new DetailProjectVM(0, "");
        this.issue = new IssueVM("", "", "");
    }

    public String getDescription() {
        return this.issue.getDescription()
            .replaceAll(System.lineSeparator(), "<br/>");
    }

}
