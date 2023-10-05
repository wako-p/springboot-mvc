package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.index;

import jp.wako.demo.springbootmvc.usecase.issues.search.ProjectDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class IndexProjectVM {

    private Integer id;
    private String name;

    public static IndexProjectVM createFrom(final ProjectDto dto) {
        var vm = new IndexProjectVM(dto.getId(), dto.getName());
        return vm;
    }

}

