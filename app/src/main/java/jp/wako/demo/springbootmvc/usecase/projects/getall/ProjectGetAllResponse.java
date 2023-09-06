package jp.wako.demo.springbootmvc.usecase.projects.getall;

import java.util.List;

import jp.wako.demo.springbootmvc.domain.projects.Project;
import lombok.Data;

@Data
public class ProjectGetAllResponse {
    private final List<Project> projects;
}
