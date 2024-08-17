package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class CreateIssueVM {

    @NotBlank(message = "Please enter a title for the issue")
    private String title;
    private String description;

    public CreateIssueVM() {
        this.title = "";
        this.description = "";
    }

}
