package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import java.util.ArrayList;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import jp.wako.demo.springbootmvc.presentation.shared.viewmodel.SearchableVM;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueSearchResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public final class IssueSearchVM extends SearchableVM {

    private Project project;
    private IssueSearchParameter parameter;
    private Page<IssueSearchResult> pages;

    public IssueSearchVM() {
        this.project = new Project("", "");
        this.parameter = new IssueSearchParameter("");
        this.pages = new PageImpl<>(new ArrayList<>());
        this.page = 0;
        this.size = 0;
    }

    public void loadFrom(
        final IssueSearchResponse issueSearchResponse,
        final Pageable pageable) {

            this.project = Project.create(issueSearchResponse.getProject());

            this.page = pageable.getPageNumber();
            this.size = pageable.getPageSize();

            var total = issueSearchResponse.getIssues().size();
            var content = issueSearchResponse.getIssues()
                .stream()
                .skip(this.size * this.page)
                .limit(this.size)
                .map(IssueSearchResult::create)
                .collect(Collectors.toList());

            this.pages = new PageImpl<>(content, pageable, total);
    }

}
