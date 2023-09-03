package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class IssueEditVM {

    private Integer id;

    @NotBlank(message = "Please enter a title for the issue")
    private String title;

    private String description;
    private Integer version;

    public IssueEditVM() {
        this.id = 0;
        this.title = "";
        this.description = "";
        this.version = 0;
    }

}
