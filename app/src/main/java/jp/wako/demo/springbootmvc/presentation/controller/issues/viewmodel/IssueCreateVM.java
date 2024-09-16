package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public final class IssueCreateVM {

    private Project project;

    @Valid
    private Issue issue;

    public IssueCreateVM() {
        this.project = new Project("", "");
        this.issue = new Issue("", "", "");
    }


}
