package jp.wako.demo.springbootmvc.usecase.issues.search;

public interface IIssueSearchQuery {
    IssueSearchResponse execute(final IssueSearchRequest request);
}
