package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.detail;

import jp.wako.demo.springbootmvc.usecase.issues.get.IssueDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class DetailIssueVM {

    private Integer id;
    private String title;
    private String description;

    public static DetailIssueVM createFrom(final IssueDto dto) {

        var vm = new DetailIssueVM(
            dto.getId(),
            dto.getTitle(),
            dto.getDescription());

        return vm;
    }

}
