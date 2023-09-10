package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jp.wako.demo.springbootmvc.usecase.issues.search.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class ProjectVM {

    private Integer id;
    private String name;

    public static ProjectVM createFrom(final ProjectDto dto) {
        var vm = new ProjectVM(dto.getId(), dto.getName());
        return vm;
    }

}

