package jp.wako.demo.springbootmvc.usecase.projects.create;

import lombok.Data;

@Data
public final class ProjectCreateRequest {
    private final String name;
    private final String description;
}
