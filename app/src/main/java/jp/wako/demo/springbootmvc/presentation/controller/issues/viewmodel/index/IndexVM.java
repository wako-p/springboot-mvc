package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.index;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public final class IndexVM {

    private IndexProjectVM project;
    private List<IndexIssueVM> issues;

    public IndexVM() {
        this.project = new IndexProjectVM(0, "");
        this.issues = new ArrayList<>();
    }

}
