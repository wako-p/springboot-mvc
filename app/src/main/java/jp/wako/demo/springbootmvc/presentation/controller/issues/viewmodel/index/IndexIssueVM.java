package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.index;

import jp.wako.demo.springbootmvc.usecase.issues.search.IssueDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class IndexIssueVM {

    private Integer id;
    private String title;
    private String description;

    public static IndexIssueVM createFrom(final IssueDto dto) {

        var vm = new IndexIssueVM(
            dto.getId(),
            dto.getTitle(),
            dto.getDescription());

        return vm;
    }

}
