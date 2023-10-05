package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel.edit;

import jakarta.validation.constraints.NotBlank;
import jp.wako.demo.springbootmvc.usecase.issues.get.IssueDto;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public final class EditIssueVM {

    private Integer id;

    @NotBlank(message = "Please enter a title for the issue")
    private String title;
    private String description;

    public static EditIssueVM createFrom(final IssueDto dto) {

        var vm = new EditIssueVM(
            dto.getId(),
            dto.getTitle(),
            dto.getDescription());

        return vm;
    }

}
