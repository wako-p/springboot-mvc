package jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class ProjectIndexVM {

    private List<ProjectVM> projects;

    public ProjectIndexVM() {
        this.projects = new ArrayList<>();
    }

}
