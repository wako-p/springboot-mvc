package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public final class IssueSearchVM {

    private Project project;
    private List<Issue> issues;

    public IssueSearchVM() {
        this.project = new Project("", "");
        this.issues = new ArrayList<>();
    }

}
