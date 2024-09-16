package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public final class EditVM {

    private Project project;

    @Valid
    private Issue issue;

    public EditVM() {
        this.project = new Project("", "");
        this.issue = new Issue("", "", "");
    }

}
