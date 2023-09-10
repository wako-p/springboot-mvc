package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public final class IssueIndexVM {

    private ProjectVM project;
    private List<IssueVM> issues;

    public IssueIndexVM() {
        this.project = new ProjectVM(0, "");
        this.issues = new ArrayList<>();
    }

}
