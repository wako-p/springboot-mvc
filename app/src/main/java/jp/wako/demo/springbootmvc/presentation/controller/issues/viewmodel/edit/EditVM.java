package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.edit;

import jakarta.validation.Valid;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import lombok.Data;

@Data
public final class EditVM {

    private EditProjectVM project;

    @Valid
    private IssueVM issue;

    public EditVM() {
        this.project = new EditProjectVM(0, "");
        this.issue = new IssueVM("", "", "");
    }

}
