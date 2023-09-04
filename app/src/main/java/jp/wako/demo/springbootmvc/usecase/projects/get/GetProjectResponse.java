package jp.wako.demo.springbootmvc.usecase.projects.get;

import lombok.Data;

@Data
public class GetProjectResponse {
    private final Integer id;
    private final String name;
    private final String description;
}
