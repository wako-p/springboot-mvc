package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.create;

import jakarta.validation.Valid;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import lombok.Data;

@Data
public final class CreateVM {

    private CreateProjectVM project;

    @Valid
    private IssueVM issue;

    public CreateVM() {
        this.project = new CreateProjectVM(0, "");
        this.issue = new IssueVM("", "", "");
    }

}
