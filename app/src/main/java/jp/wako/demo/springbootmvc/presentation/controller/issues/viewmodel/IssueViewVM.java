package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import lombok.Data;

@Data
public final class IssueViewVM {

    private Integer id;
    private String title;
    private String description;

    public IssueViewVM() {
        this.id = 0;
        this.title = "";
        this.description = "";
    }

    public String getDescription() {
        return this.description
            .replaceAll(System.lineSeparator(), "<br/>");
    }

}
