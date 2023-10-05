package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.detail;

import jp.wako.demo.springbootmvc.usecase.issues.get.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class DetailProjectVM {

    private Integer id;
    private String name;

    public static DetailProjectVM createFrom(final ProjectDto dto) {
        var vm = new DetailProjectVM(dto.getId(), dto.getName());
        return vm;
    }
}

