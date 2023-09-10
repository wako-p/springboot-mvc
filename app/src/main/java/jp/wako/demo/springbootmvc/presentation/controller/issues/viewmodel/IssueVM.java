package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jp.wako.demo.springbootmvc.usecase.issues.search.IssueDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class IssueVM {

    private Integer id;
    private Integer projectId;
    private String title;
    private String description;

    public static IssueVM createFrom(final IssueDto dto) {

        var vm = new IssueVM(
            dto.getId(),
            dto.getProjectId(),
            dto.getTitle(),
            dto.getDescription());

        return vm;
    }

}
