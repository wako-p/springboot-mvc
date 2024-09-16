package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.create;

import jakarta.validation.Valid;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.ProjectVM;
import lombok.Data;

@Data
public final class CreateVM {

    private ProjectVM project;

    @Valid
    private IssueVM issue;

    public CreateVM() {
        this.project = new ProjectVM("", "");
        this.issue = new IssueVM("", "", "");
    }

}
