package jp.wako.demo.springbootmvc.presentation.controller.issues.viewmodel;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class IssueVM {
    private final Integer id;
    private final String title;
    private final String description;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
