package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import jakarta.validation.constraints.NotEmpty;
import jp.wako.demo.springbootmvc.usecase.issues.fetch.IssueFetchResponse;
import jp.wako.demo.springbootmvc.usecase.issues.search.IssueDto;
import lombok.Data;

@Data
public final class Issue {

    private String id;
    private String projectId;

    @NotEmpty(message = "Please enter a title for the issue")
    private String title;
    private String description;

    public Issue(
        final String id,
        final String projectId,
        final String title,
        final String description
    ) {
        this.id = id;
        this.projectId = projectId;
        this.title = title;
        this.description = description;
    }

    public static Issue create(final IssueDto dto) {
        return new Issue(
            dto.getId().toString(),
            dto.getProjectId().toString(),
            dto.getTitle(),
            dto.getDescription());
    }

    public static Issue create(final IssueFetchResponse response) {
        return new Issue(
            response.getId().toString(),
            response.getProjectId().toString(),
            response.getTitle(),
            response.getDescription());
    }

}
