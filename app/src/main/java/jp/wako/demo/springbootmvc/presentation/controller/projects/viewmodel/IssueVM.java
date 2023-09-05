package jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel;

import lombok.Data;

@Data
public final class IssueVM {
    private final Integer id;
    private final String title;
    private final String description;
}
