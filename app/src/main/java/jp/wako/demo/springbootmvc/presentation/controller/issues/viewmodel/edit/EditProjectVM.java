package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.edit;

import jp.wako.demo.springbootmvc.usecase.issues.get.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class EditProjectVM {

    private Integer id;
    private String name;

    public static EditProjectVM createFrom(final ProjectDto dto) {
        var vm = new EditProjectVM(dto.getId(), dto.getName());
        return vm;
    }
}

