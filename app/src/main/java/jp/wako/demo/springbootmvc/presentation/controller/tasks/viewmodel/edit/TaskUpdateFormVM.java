package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.edit;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class TaskUpdateFormVM {

    private String id;

    @NotBlank(message = "Please enter a title for the task")
    private String title;

    private String description;
    private boolean done;

    public TaskUpdateFormVM() {
        this.id = "No id";
        this.title = "No title";
        this.description = "No description";
        this.done = false;
    }

}
