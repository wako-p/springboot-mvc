package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.detail;

import lombok.Data;

@Data
public final class TaskDetailVM {

    private TaskUpdateFormVM form;

    public TaskDetailVM() {
        this.form = new TaskUpdateFormVM();
    }

}