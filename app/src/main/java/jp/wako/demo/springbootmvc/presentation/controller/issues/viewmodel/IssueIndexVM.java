package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import java.util.ArrayList;
import java.util.List;

import jakarta.validation.Valid;
import lombok.Data;

@Data
public final class IssueIndexVM {

    @Valid
    private IssueCreateVM issueCreateVM;
    private List<IssueVM> issues;

    public IssueIndexVM() {
        this.issueCreateVM = new IssueCreateVM();
        this.issues = new ArrayList<IssueVM>();
    }

}
