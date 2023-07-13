package jp.wako.demo.springbootmvc.presentation.controller.tasks.model.detail;

import lombok.Data;

@Data
public final class TaskUpdateFormVM {

    private String id;
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
