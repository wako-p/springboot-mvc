package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.index;

import java.util.ArrayList;
import java.util.List;

import jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.IssueVM;
import lombok.Data;

@Data
public final class IndexVM {

    private IndexProjectVM project;
    private List<IssueVM> issues;

    public IndexVM() {
        this.project = new IndexProjectVM(0, "");
        this.issues = new ArrayList<>();
    }

}
