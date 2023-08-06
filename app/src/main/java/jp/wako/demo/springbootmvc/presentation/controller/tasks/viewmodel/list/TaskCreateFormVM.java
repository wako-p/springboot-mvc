package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.list;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class TaskCreateFormVM {

    @NotBlank(message = "Please enter a title for the task")
    private String title;

    public TaskCreateFormVM() {
        this.title = "";
    }

}
