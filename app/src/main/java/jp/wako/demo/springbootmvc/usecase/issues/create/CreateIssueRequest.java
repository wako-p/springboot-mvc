package jp.wako.demo.springbootmvc.usecase.issues.create;

import lombok.Data;

@Data
public final class CreateIssueRequest {
    private final String title;
}
