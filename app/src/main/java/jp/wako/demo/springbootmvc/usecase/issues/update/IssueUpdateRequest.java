package jp.wako.demo.springbootmvc.usecase.issues.update;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public final class IssueUpdateRequest {
    private final Long id;
    private final String title;
    private final String description;
}
