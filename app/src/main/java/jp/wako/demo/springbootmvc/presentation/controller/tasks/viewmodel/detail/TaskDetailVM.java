package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.detail;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public final class TaskDetailVM {

    @Valid
    private TaskUpdateFormVM form;

    public TaskDetailVM() {
        this.form = new TaskUpdateFormVM();
    }

}
