package jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel;

import java.util.List;

import lombok.Data;

@Data
public class ProjectIssuesVM {
    private String name;
    private List<IssueVM> issues;
}
