package jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class ProjectCreateVM {

    @NotBlank(message = "Please enter a name for the project")
    private String name;
    private String description;

    public ProjectCreateVM() {
        this.name = "";
        this.description = "";
    }

}
