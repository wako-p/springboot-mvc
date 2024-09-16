package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jakarta.validation.constraints.NotEmpty;
import jp.wako.demo.springbootmvc.usecase.issues.get.IssueDto;
import lombok.Data;

@Data
public final class IssueVM {

    private String id;

    @NotEmpty(message = "Please enter a title for the issue")
    private String title;
    private String description;

    public IssueVM(
        final String id,
        final String title,
        final String description
    ) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public static IssueVM createFrom(final IssueDto dto) {

        var vm = new IssueVM(
            dto.getId().toString(),
            dto.getTitle(),
            dto.getDescription());

        return vm;
    }

    public static IssueVM createFrom(final jp.wako.demo.springbootmvc.usecase.issues.search.IssueDto dto) {
        var vm = new IssueVM(
            dto.getId().toString(),
            dto.getTitle(),
            dto.getDescription());

        return vm;
    }

}
