package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public final class TaskCreateVM {

    @NotBlank(message = "Please enter a title for the task")
    private String title;

    public TaskCreateVM() {
        this.title = "";
    }

}
