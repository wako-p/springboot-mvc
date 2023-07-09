package jp.wako.demo.springbootmvc.presentation.controller.task.model.list;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class TaskAddFormVM {

    @NotBlank(message = "Please enter a title for the task")
    private String title;

    public TaskAddFormVM() {
        this.title = "";
    }

}