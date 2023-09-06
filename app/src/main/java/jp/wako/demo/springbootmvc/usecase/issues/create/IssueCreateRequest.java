package jp.wako.demo.springbootmvc.usecase.issues.create;

import lombok.Data;

@Data
public final class IssueCreateRequest {
    private final String title;
}
