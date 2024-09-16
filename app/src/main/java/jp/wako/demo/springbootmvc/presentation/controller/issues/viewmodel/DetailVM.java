package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import lombok.Data;

@Data
public final class DetailVM {

    private Project project;
    private Issue issue;

    public DetailVM() {
        this.project = new Project("", "");
        this.issue = new Issue("", "", "");
    }

    public String getDescription() {
        return this.issue.getDescription()
            .replaceAll(System.lineSeparator(), "<br/>");
    }

}
