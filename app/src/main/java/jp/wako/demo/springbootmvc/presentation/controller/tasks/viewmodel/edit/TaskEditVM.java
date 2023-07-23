package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.edit;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public final class TaskEditVM {

    @Valid
    private TaskUpdateFormVM form;

    public TaskEditVM() {
        this.form = new TaskUpdateFormVM();
    }

}
