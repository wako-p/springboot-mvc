package jp.wako.demo.springbootmvc.presentation.controller.projects.viewmodel;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ProjectVM {
    private Integer id;
    private String name;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
