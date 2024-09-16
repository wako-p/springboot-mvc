package jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel;

import lombok.Data;

@Data
public final class ProjectViewVM {

    private String id;
    private String name;
    private String description;

    public ProjectViewVM() {
        this.id = "";
        this.name = "";
        this.description = "";
    }

    public String getDescription() {
        return this.description
            .replaceAll(System.lineSeparator(), "<br/>");
    }

}
