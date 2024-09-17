package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jp.wako.demo.springbootmvc.usecase.projects.fetch.ProjectFetchResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Project {

    private String id;
    private String name;

    public static Project create(final ProjectFetchResponse response) {
        return new Project(
            response.getProject().getId().toString(),
            response.getProject().getName());
    }

}
