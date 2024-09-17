package jp.wako.demo.springbootmvc.usecase.projects.fetch;

import lombok.Data;

@Data
public final class ProjectDto {
    private final Long id;
    private final String name;
    private final String description;
}
