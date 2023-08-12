package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class TaskEditVM {

    private Integer id;

    @NotBlank(message = "Please enter a title for the task")
    private String title;

    private String description;
    private Integer version;

    public TaskEditVM() {
        this.id = 0;
        this.title = "";
        this.description = "";
        this.version = 0;
    }

}
