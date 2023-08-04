package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.edit;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class TaskEditVM {

    private int id;

    @NotBlank(message = "Please enter a title for the task")
    private String title;

    private String description;

    public TaskEditVM() {
        this.id = 0;
        this.title = "";
        this.description = "";
    }

}
