package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jp.wako.demo.springbootmvc.usecase.issues.get.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class ProjectVM {

    private String id;
    private String name;

    public static ProjectVM createFrom(final ProjectDto dto) {
        var vm = new ProjectVM(
            dto.getId().toString(),
            dto.getName());
        return vm;
    }

    public static ProjectVM createFrom(final jp.wako.demo.springbootmvc.usecase.issues.search.ProjectDto dto) {
        var vm = new ProjectVM(
            dto.getId().toString(),
            dto.getName());
        return vm;
    }
}
