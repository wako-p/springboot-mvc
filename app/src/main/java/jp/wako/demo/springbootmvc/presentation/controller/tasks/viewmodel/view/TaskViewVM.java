package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel.view;

import lombok.Data;

@Data
public final class TaskViewVM {

    private String id;
    private String title;
    private String description;

    public TaskViewVM() {
        this.id = "";
        this.title = "";
        this.description = "";
    }

}
