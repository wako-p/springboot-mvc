package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.index;

import java.util.ArrayList;
import java.util.List;

import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.ProjectVM;
import lombok.Data;

@Data
public final class IndexVM {

    private ProjectVM project;
    private List<IssueVM> issues;

    public IndexVM() {
        this.project = new ProjectVM("", "");
        this.issues = new ArrayList<>();
    }

}
