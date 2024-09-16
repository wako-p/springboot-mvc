package jp.wako.demo.springbootmvc.usecase.issues;

import java.time.LocalDateTime;

import jp.wako.demo.springbootmvc.domain.issues.Issue;

public class TestIssueFactory {

    public static Issue create() {
        var issue = Issue.reconstruct(
            1000L,
            1000L,
            "Issue1",
            "This is a test issue.",
            LocalDateTime.now().withNano(0),
            LocalDateTime.now().withNano(0),
            1L);
        return issue;
    }

    public static Issue create(final Long id) {
        var issue = Issue.reconstruct(
            id,
            1000L,
            "Issue1",
            "This is a test issue.",
            LocalDateTime.now().withNano(0),
            LocalDateTime.now().withNano(0),
            1L);
        return issue;
    }

    public static Issue create(final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        var issue = Issue.reconstruct(
            1000L,
            1000L,
            "Issue1",
            "This is a test issue.",
            createdAt,
            updatedAt,
            1L);
        return issue;
    }

    public static Issue create(final Long id, final LocalDateTime createdAt, final LocalDateTime updatedAt) {
        var issue = Issue.reconstruct(
            id,
            1000L,
            "Issue1",
            "This is a test issue.",
            createdAt,
            updatedAt,
            1L);
        return issue;
    }

}
