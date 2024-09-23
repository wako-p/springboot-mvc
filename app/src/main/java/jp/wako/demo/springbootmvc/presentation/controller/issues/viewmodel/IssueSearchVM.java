package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchResponse;
import lombok.Data;

@Data
public final class IssueSearchVM {

    private Project project;
    private IssueSearchParameter parameter;
    private Page<IssueSearchResult> pages;

    public IssueSearchVM() {
        this.project = new Project("", "");
        this.parameter = new IssueSearchParameter("");
        this.pages = new PageImpl<>(new ArrayList<>());
    }

    public void loadFrom(
        final IssueSearchResponse issueSearchResponse,
        final Pageable pageable) {

            this.project = Project.create(issueSearchResponse.getProject());

            var total = issueSearchResponse.getIssues().size();
            var content = issueSearchResponse.getIssues()
                .stream()
                .skip(pageable.getOffset())
                .limit(pageable.getPageSize())
                .map(IssueSearchResult::create)
                .collect(Collectors.toList());

            this.pages =  new PageImpl<>(content, pageable, total);
    }

}
