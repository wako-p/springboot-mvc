package jp.wako.demo.springbootmvc.usecase.issues.fetch;

import lombok.Data;

@Data
public final class IssueFetchResponse {
    private final Long id;
    private final Long projectId;
    private final String title;
    private final String description;
}
