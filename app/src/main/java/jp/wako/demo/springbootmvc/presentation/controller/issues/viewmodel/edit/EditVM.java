package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.edit;

import jakarta.validation.Valid;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.ProjectVM;
import lombok.Data;

@Data
public final class EditVM {

    private ProjectVM project;

    @Valid
    private IssueVM issue;

    public EditVM() {
        this.project = new ProjectVM("", "");
        this.issue = new IssueVM("", "", "");
    }

}
