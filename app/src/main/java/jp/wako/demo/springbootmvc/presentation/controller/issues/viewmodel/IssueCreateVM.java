package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jakarta.validation.Valid;
import jp.wako.demo.springbootmvc.usecase.projects.fetch.ProjectFetchResponse;
import lombok.Data;

@Data
public final class IssueCreateVM {

    private Project project;

    @Valid
    private Issue issue;

    public IssueCreateVM() {
        this.project = new Project("", "");
        this.issue = new Issue("", "", "", "");
    }

    public void loadFrom(final ProjectFetchResponse response) {
        this.project = Project.create(response);
    }

}
