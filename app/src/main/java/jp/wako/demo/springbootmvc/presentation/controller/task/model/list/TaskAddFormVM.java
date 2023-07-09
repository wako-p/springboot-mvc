package jp.wako.demo.springbootmvc.presentation.controller.task.model.list;

import lombok.Data;

@Data
public final class TaskAddFormVM {

    private String title;

    public TaskAddFormVM() {
        this.title = "";
    }

}
