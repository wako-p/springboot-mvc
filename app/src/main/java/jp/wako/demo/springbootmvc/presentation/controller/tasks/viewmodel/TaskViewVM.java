package jp.wako.demo.springbootmvc.presentation.controller.tasks.viewmodel;

import lombok.Data;

@Data
public final class TaskViewVM {

    private Integer id;
    private String title;
    private String description;

    public TaskViewVM() {
        this.id = 0;
        this.title = "";
        this.description = "";
    }

    public String getDescription() {
        return this.description.replaceAll(System.lineSeparator(), "<br/>");
    }

}
