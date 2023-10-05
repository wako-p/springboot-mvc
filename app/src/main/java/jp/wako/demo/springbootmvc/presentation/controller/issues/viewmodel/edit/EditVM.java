package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.edit;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public final class EditVM {

    private EditProjectVM project;

    @Valid
    private EditIssueVM issue;

    public EditVM() {
        this.project = new EditProjectVM(0, "");
        this.issue = new EditIssueVM(0, "", "");
    }

}
