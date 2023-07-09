package jp.wako.demo.springbootmvc.presentation.controller.task.model.list;

import lombok.Data;

@Data
public final class TaskCreateFormVM {

    private String title;

    public TaskCreateFormVM() {
        this.title = "";
    }

}
