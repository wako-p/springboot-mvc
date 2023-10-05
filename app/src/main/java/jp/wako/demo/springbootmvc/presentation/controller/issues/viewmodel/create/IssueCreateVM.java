package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.create;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class IssueCreateVM {

    private Integer projectId;

    @NotBlank(message = "Please enter a title for the issue")
    private String title;
    private String description;

    public IssueCreateVM() {
        this.projectId = 0;
        this.title = "";
        this.description = "";
    }

}
