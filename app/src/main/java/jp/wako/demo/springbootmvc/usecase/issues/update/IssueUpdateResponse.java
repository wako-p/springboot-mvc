package jp.wako.demo.springbootmvc.usecase.issues.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class IssueUpdateResponse {
    private final Long id;
    private final Long projectId;
    private final String title;
    private final String description;
}
