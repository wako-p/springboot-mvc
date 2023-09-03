package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class IssueCreateVM {

    @NotBlank(message = "Please enter a title for the issue")
    private String title;

    public IssueCreateVM() {
        this.title = "";
    }

}
