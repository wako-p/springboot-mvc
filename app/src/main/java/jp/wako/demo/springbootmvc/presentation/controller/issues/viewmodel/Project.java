package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jp.wako.demo.springbootmvc.usecase.issues.fetch.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class Project {

    private String id;
    private String name;

    public static Project createFrom(final ProjectDto dto) {
        var vm = new Project(
            dto.getId().toString(),
            dto.getName());
        return vm;
    }

    public static Project createFrom(final jp.wako.demo.springbootmvc.usecase.issues.search.ProjectDto dto) {
        var vm = new Project(
            dto.getId().toString(),
            dto.getName());
        return vm;
    }
}
