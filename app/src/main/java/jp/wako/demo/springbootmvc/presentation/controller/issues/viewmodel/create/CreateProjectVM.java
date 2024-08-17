package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.create;

import jp.wako.demo.springbootmvc.usecase.issues.get.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class CreateProjectVM {

    private Integer id;
    private String name;

    public static CreateProjectVM createFrom(final ProjectDto dto) {
        var vm = new CreateProjectVM(dto.getId(), dto.getName());
        return vm;
    }
}

